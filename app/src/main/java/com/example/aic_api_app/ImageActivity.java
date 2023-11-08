package com.example.aic_api_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

public class ImageActivity extends AppCompatActivity {

    private ImageView logoImageActivity;
    private TextView titleImageActivity;
    private TextView artist_display1_ImageActivity;
    private TextView artist_display2_ImageActivity;
    private PhotoView image_ImageActivity;
    private Picasso picasso= Picasso.get();;


    private int imageId_dummy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        logoImageActivity = findViewById(R.id.logoImageActivity);
        titleImageActivity = findViewById(R.id.titleImageActivity);
        artist_display1_ImageActivity = findViewById(R.id.artist_display1_ImageActivity);
        artist_display2_ImageActivity = findViewById(R.id.artist_display2_ImageActivity);

        imageId_dummy = R.drawable.not_available;
        image_ImageActivity = findViewById(R.id.image_ImageActivity);
        image_ImageActivity.setImageResource(imageId_dummy);
        image_ImageActivity.setMaximumScale(13f);
        image_ImageActivity.setMediumScale(4f);
        image_ImageActivity.setMinimumScale(1f);

        logoImageActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        Artwork artwork = (Artwork) getIntent().getSerializableExtra("selectedArtwork");
        populateData(artwork);
    }

    private void populateData(Artwork artwork) {
        titleImageActivity.setText(artwork.getTitle());
        String[] splitArtist = artwork.getArtist_display().split("\n");
        artist_display1_ImageActivity.setText(splitArtist[0]);
        if (splitArtist.length > 1) {
            artist_display2_ImageActivity.setText(splitArtist[1]);
        } else {
            artist_display2_ImageActivity.setVisibility(View.GONE);
        }

        String imageUrl = artwork.getImageUrl();

        picasso.load(imageUrl)
                .error(R.drawable.not_available)
                .into(image_ImageActivity);
    }
}