package com.example.aic_api_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    private ProgressBar progressBar;
    private SearchVolley searchVolley;

    ImageView clearImageView;
    EditText editText;
    TextView copyrightNotice;

    private final List<Artwork> ArtworkList = new ArrayList<>();
    private ArtworkFetcher_extraCredit artworkFetcher;

    private RecyclerView recyclerView;

    private ArtworkAdapter Artadapter;
    private LinearLayoutManager linearLayoutManager;
    private Button randomButton;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkNet(null);
        setContentView(R.layout.activity_main);


        clearImageView = findViewById(R.id.clearImageView);
        editText = findViewById(R.id.searchEditText);
        copyrightNotice = findViewById(R.id.copyrightTextView);


        recyclerView = findViewById(R.id.recyclerView);
        Artadapter = new ArtworkAdapter(ArtworkList, this);
        recyclerView.setAdapter(Artadapter);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        searchVolley = new SearchVolley(this);


        progressBar = findViewById(R.id.progressBar);

        //    extra credit
        randomButton = findViewById(R.id.randomButton);
        artworkFetcher = new ArtworkFetcher_extraCredit(searchVolley.getRequestQueue(), new GalleryFetcher_extraCredit(searchVolley.getRequestQueue()));
    }

    //    extra credit
    public void fetchRandomArtworks(View view) {
        progressBar.setVisibility(View.VISIBLE);
        artworkFetcher.fetchRandomArtwork(new ArtworkFetcher_extraCredit.ArtworkFetcherCallback() {
            @Override
            public void onArtworkFetched(Artwork artwork) {
                progressBar.setVisibility(View.GONE);
                if (isValidArtwork(artwork)) {
                    ArrayList<Artwork> randomArtworks = new ArrayList<>();
                    randomArtworks.add(artwork);
                    Artadapter.updateData(randomArtworks);
                    if (randomArtworks.isEmpty()) {
                        recyclerView.setBackgroundResource(R.drawable.bwlions);
                    } else {
                        recyclerView.setBackground(null);
                    }
                } else {
                    showErrorDialog("Error", "Incomplete artwork information. Please try again.");
                }
            }
            @Override
            public void onError(Exception error) {
                progressBar.setVisibility(View.GONE);
                showErrorDialog("Error", "Error: " + error.getMessage());
            }
        });
    }
    private boolean isValidArtwork(Artwork artwork) {
        if (artwork == null) return false;
        if (artwork.getTitle() == null
                || artwork.getTitle().isEmpty()
                || artwork.getGallery_title() == null || artwork.getGallery_id() == null
                || artwork.getArtwork_type_title() == null
                || artwork.getDepartment_title() == null
                || artwork.getArtist_display() == null) return false;
        return true;
    }
    public void onSearchButtonClicked(View view) {
        String query = editText.getText().toString();
        if (query.trim().length() < 3) {
            showErrorDialog("Search string too short ", "Please try a longer search string");
        } else if (!hasNetworkConnection()) {
            showErrorDialog("NoConnectionError", "No network connection present - cannot contact Art Institute API.");
        } else {
            performSearch(query);
        }
    }

    //    error conditions
    private void showErrorDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.logo)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }


    private void performSearch(String query) {
        progressBar.setVisibility(View.VISIBLE);
        hideKeyboard();
        recyclerView.setBackground(null);

        searchVolley.doSearch(query, 15, 1, new SearchVolley.ArtworkSearchCallback() {
            @Override
            public void onSuccess(ArrayList<Artwork> artworks) {

                progressBar.setVisibility(View.GONE);
                if (artworks.isEmpty()) {
                    showErrorDialog("No search results found ", "No results found for '" + query + "'. " + "Please try another search string.");
                } else {
                    Artadapter.updateData(artworks);
                    if (artworks.isEmpty()) {
                        recyclerView.setBackgroundResource(R.drawable.bwlions);
                    } else {
                        recyclerView.setBackground(null);
                    }
                }
            }

            @Override
            public void onError(String error) {
                progressBar.setVisibility(View.GONE);
                showErrorDialog("Error", "Error: " + error);
            }
        });
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }


    public void checkNet(View v) {
        if (hasNetworkConnection())
            Toast.makeText(this, R.string.has_net, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, R.string.no_net, Toast.LENGTH_SHORT).show();
    }

    //    network
    private boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }
    public void doClear(View v) {
        editText.setText("");
    }

    public void doCopyRightClick(View v) {
        Intent intent = new Intent(MainActivity.this, CopyrightActivity.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {  // click listener called by ViewHolder clicks
        int pos = recyclerView.getChildLayoutPosition(v);
        Artwork artworkSelected = Artadapter.getArtwork(pos);
        Toast.makeText(v.getContext(), "Selected: " + artworkSelected.getTitle(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, ArtworkActivity.class);
        intent.putExtra("selectedArtwork", artworkSelected);
        startActivity(intent);
    }

    @Override
    public boolean onLongClick(View v) {  // long click listener called by ViewHolder long clicks
        Toast.makeText(v.getContext(), "LONG click", Toast.LENGTH_SHORT).show();
        return true;
    }
}