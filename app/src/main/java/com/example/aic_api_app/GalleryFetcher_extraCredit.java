package com.example.aic_api_app;

import android.net.Uri;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class GalleryFetcher_extraCredit {

    private final RequestQueue requestQueue;
    private ArrayList<String> galleryIds = new ArrayList<>();

    public interface GalleryFetcherCallback {
        void onGalleryIdsFetched(ArrayList<String> galleryIds);
        void onError(Exception error);
    }

    public GalleryFetcher_extraCredit(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public void fetchGalleryIds(final int page, final GalleryFetcherCallback callback) {
        Uri.Builder builder = Uri.parse("https://api.artic.edu/api/v1/galleries").buildUpon();
        builder.appendQueryParameter("limit", "100");
        builder.appendQueryParameter("fields", "id");
        builder.appendQueryParameter("page", String.valueOf(page));

        String url = builder.toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                galleryIds.add(data.getJSONObject(i).getString("id"));
                            }
                            JSONObject pagination = response.getJSONObject("pagination");
                            if (pagination.getInt("current_page") < pagination.getInt("total_pages")) {
                                fetchGalleryIds(page + 1, callback);
                            } else {
                                callback.onGalleryIdsFetched(galleryIds);
                            }
                        } catch (Exception e) {
                            callback.onError(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }





}
