package com.example.aic_api_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class ArtworkActivity extends AppCompatActivity {
    private ImageView logoImageView;
    private TextView title;
    private TextView dateDisplay;
    private TextView artist_display1;
    private TextView artist_display2;
    private ImageView image;
    private TextView department_title;
    private TextView gallery_title;
    private ImageView galleryExternalResource;
    private TextView place_of_origin;
    private TextView artwork_type_titleAndMedium_display;
    private TextView dimensions;
    private TextView credit_line;
    private String galleryLink;
    private Picasso picasso= Picasso.get();;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork);
        title = findViewById(R.id.title_ArtworkActivity);
        dateDisplay = findViewById(R.id.dateDisplayArtworkActivity);
        artist_display1 = findViewById(R.id.artist_display1_ArtworkActivity);
        artist_display2 = findViewById(R.id.artist_display2_ArtworkActivity);
        image = findViewById(R.id.image_ArtworkActivity);
        department_title = findViewById(R.id.department_titleArtworkActivity);
        gallery_title = findViewById(R.id.gallery_titleArtworkActivity);
        galleryExternalResource = findViewById(R.id.galleryExternalResource);
        place_of_origin = findViewById(R.id.place_of_originArtworkActivity);
        artwork_type_titleAndMedium_display = findViewById(R.id.artwork_type_title_medium_displayArtworkActivity);
        dimensions = findViewById(R.id.dimensionsArtworkActivity);
        credit_line = findViewById(R.id.credit_line_artworkActivity);


        logoImageView = findViewById(R.id.Logo_ArtworkActivity);
        logoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArtworkActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
        Artwork artwork = (Artwork) getIntent().getSerializableExtra("selectedArtwork");
        populateData(artwork);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArtworkActivity.this, ImageActivity.class);
                intent.putExtra("selectedArtwork", artwork);
                startActivity(intent);
            }
        });
    }
    private void populateData(Artwork artwork) {
        if (artwork != null) {
            title.setText(artwork.getTitle());

            String[] splitArtist = artwork.getArtist_display().split("\n");
                artist_display1.setText(splitArtist[0]);
                artist_display2.setText(splitArtist[1]);
            department_title.setText(artwork.getDepartment_title());
            place_of_origin.setText(artwork.getPlace_of_origin());
            artwork_type_titleAndMedium_display.setText(artwork.getArtwork_type_title() + " - " + artwork.getMedium_display());
            dimensions.setText(artwork.getDimensions());
            credit_line.setText(artwork.getCredit_line());
            dateDisplay.setText(artwork.getDate_display());
            String imageUrl = artwork.getImageUrl();
            picasso.load(imageUrl)
                    .error(R.drawable.not_available)
                    .resize(800, 800)
                    .centerInside()
                    .into(image);
            galleryLink = "https://www.artic.edu/galleries/" + artwork.getGallery_id();

            if (artwork.getGallery_id() != null && !artwork.getGallery_id().isEmpty()){

                    gallery_title.setText(artwork.getGallery_title());

                galleryExternalResource.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGalleryWebPage(v);
                    }
                });

                } else {
                    gallery_title.setText("Not on Display");
                    galleryExternalResource.setVisibility(View.GONE); // hide the ImageView
                }
        }
    }

    public void openGalleryWebPage(View view){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(galleryLink));
            startActivity(browserIntent);
    }
}