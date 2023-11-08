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
import java.util.Random;

public class ArtworkFetcher_extraCredit {
    private final RequestQueue requestQueue;
    private final GalleryFetcher_extraCredit galleryFetcher;
    private final Random random = new Random();
    public interface ArtworkFetcherCallback {
        void onArtworkFetched(Artwork artwork);
        void onError(Exception error);
    }
    public ArtworkFetcher_extraCredit(RequestQueue requestQueue, GalleryFetcher_extraCredit galleryFetcher) {
        this.requestQueue = requestQueue;
        this.galleryFetcher = galleryFetcher;
    }
    private void fetchArtworkFromGallery(String galleryId, final ArtworkFetcherCallback callback) {
        fetchArtwork(galleryId, callback);
    }
    private void fetchArtwork(String galleryId, final ArtworkFetcherCallback callback) {
        Uri.Builder builder = Uri.parse("https://api.artic.edu/api/v1/artworks/search?query[term][gallery_id]=" + galleryId).buildUpon();
        builder.appendQueryParameter("limit", "100");
        builder.appendQueryParameter("fields", "title,date_display,artist_display,medium_display,artwork_type_title,image_id,dimensions,department_title,credit_line,place_of_origin,gallery_title,gallery_id,id,api_link");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, builder.toString(), null,
                response -> {
                    try {
                        JSONArray data = response.getJSONArray("data");
                        if (data.length() == 0) {
                            fetchRandomArtwork(callback);
                            return;
                        }
                        ArrayList<Artwork> artworks = new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject artworkObj = data.getJSONObject(i);
                            artworks.add(parseArtwork(artworkObj));
                        }
                        int randomIndex = random.nextInt(artworks.size());
                        callback.onArtworkFetched(artworks.get(randomIndex));
                    } catch (Exception e) {
                        callback.onError(e);
                    }
                },
                callback::onError
        );

        requestQueue.add(jsonObjectRequest);
    }
    void fetchRandomArtwork(final ArtworkFetcherCallback callback) {
        galleryFetcher.fetchGalleryIds(1, new GalleryFetcher_extraCredit.GalleryFetcherCallback() {
            @Override
            public void onGalleryIdsFetched(ArrayList<String> galleryIds) {
                if (galleryIds.isEmpty()) {
                    callback.onError(new Exception("No galleries found"));
                    return;
                }
                String randomGalleryId = galleryIds.get(random.nextInt(galleryIds.size()));
                fetchArtworkFromGallery(randomGalleryId, callback);
            }

            @Override
            public void onError(Exception error) {
                callback.onError(error);
            }
        });
    }

    private Artwork parseArtwork(JSONObject artworkObj) throws Exception {
        return new Artwork(
                artworkObj.getString("medium_display"),
                artworkObj.getString("artist_display"),
                artworkObj.getString("title"),
                artworkObj.getString("gallery_title"),
                artworkObj.getString("place_of_origin"),
                artworkObj.getString("credit_line"),
                artworkObj.getString("artwork_type_title"),
                artworkObj.getString("department_title"),
                artworkObj.getString("api_link"),
                artworkObj.getString("date_display"),
                artworkObj.getString("gallery_id"),
                artworkObj.getString("id"),
                artworkObj.getString("image_id"),
                artworkObj.getString("dimensions")
        );
    }
}