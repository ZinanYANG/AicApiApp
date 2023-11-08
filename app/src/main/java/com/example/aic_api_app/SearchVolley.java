package com.example.aic_api_app;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchVolley {

    private Context mContext;
    private RequestQueue mRequestQueue;

    private static final String BASE_URL = "https://api.artic.edu/api/v1/artworks/search";
    private static final String DEFAULT_FIELDS = "title,date_display,artist_display,medium_display,artwork_type_title,image_id,dimensions,department_title,credit_line,place_of_origin,gallery_title,gallery_id,id,api_link";

//  extra credit
    private static final String GALLERIES_URL = "https://api.artic.edu/api/v1/galleries";

    public SearchVolley(Context context) {
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(mContext);
    }

    public void doSearch(String query, int limit, int page, final ArtworkSearchCallback callback) {
        Uri.Builder uriBuilder = Uri.parse(BASE_URL).buildUpon();
        uriBuilder.appendQueryParameter("q", query);
        uriBuilder.appendQueryParameter("limit", String.valueOf(limit));
        uriBuilder.appendQueryParameter("page", String.valueOf(page));
        uriBuilder.appendQueryParameter("fields", DEFAULT_FIELDS);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, uriBuilder.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray data = jsonResponse.getJSONArray("data");
                            ArrayList<Artwork> artworks = new ArrayList<>();

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject artworkObj = data.getJSONObject(i);

                                String medium_display = artworkObj.optString("medium_display", "").equals("null") ? "" : artworkObj.getString("medium_display");
                                String artist_display = artworkObj.optString("artist_display", "").equals("null") ? "" : artworkObj.getString("artist_display");
                                String title = artworkObj.optString("title", "").equals("null") ? "" : artworkObj.getString("title");
                                String gallery_title = artworkObj.optString("gallery_title", "").equals("null") ? "" : artworkObj.getString("gallery_title");
                                String place_of_origin = artworkObj.optString("place_of_origin", "").equals("null") ? "" : artworkObj.getString("place_of_origin");
                                String credit_line = artworkObj.optString("credit_line", "").equals("null") ? "" : artworkObj.getString("credit_line");
                                String artwork_type_title = artworkObj.optString("artwork_type_title", "").equals("null") ? "" : artworkObj.getString("artwork_type_title");
                                String department_title = artworkObj.optString("department_title", "").equals("null") ? "" : artworkObj.getString("department_title");
                                String api_link = artworkObj.optString("api_link", "").equals("null") ? "" : artworkObj.getString("api_link");
                                String date_display = artworkObj.optString("date_display", "").equals("null") ? "" : artworkObj.getString("date_display");
                                String gallery_id = artworkObj.optString("gallery_id", "").equals("null") ? "" : artworkObj.getString("gallery_id");
                                String id = artworkObj.optString("id", "").equals("null") ? "" : artworkObj.getString("id");
                                String image_id = artworkObj.optString("image_id", "").equals("null") ? "" : artworkObj.getString("image_id");
                                String dimensions = artworkObj.optString("dimensions", "").equals("null") ? "" : artworkObj.getString("dimensions");
                                Artwork artwork = new Artwork(
                                        medium_display,
                                        artist_display,
                                        title,
                                        gallery_title,
                                        place_of_origin,
                                        credit_line,
                                        artworkObj.getString("artwork_type_title"),
                                        department_title,
                                        api_link,
                                        date_display,
                                        gallery_id,
                                        id,
                                        image_id,
                                        dimensions
                                );
                                String imageId = artworkObj.getString("image_id");

                                artworks.add(artwork);
                            }
                            callback.onSuccess(artworks);
                        } catch (Exception e) {
                            callback.onError(e.toString());
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.toString());
                        Log.d("SearchVolley", "Error: " + error.toString());
                    }
                });

        mRequestQueue.add(stringRequest);
    }
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public interface ArtworkSearchCallback {
        void onSuccess(ArrayList<Artwork> artworks);
        void onError(String error);
    }


}



