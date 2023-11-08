package com.example.aic_api_app;



import androidx.annotation.NonNull;

import java.io.Serializable;

public class Artwork implements Serializable{

    private String medium_display;
    private String artist_display;
    private String title;
    private String gallery_title;
    private String place_of_origin;
    private String credit_line;
    private String artwork_type_title;
    private String department_title;
    private String api_link;
    private String date_display;
    private String gallery_id;
    private String id;
    private String image_id;
    private String dimensions;

    public Artwork(String medium_display, String artist_display, String title, String gallery_title, String place_of_origin, String credit_line, String artwork_type_title, String department_title, String api_link, String date_display, String gallery_id, String id, String image_id, String dimensions) {
        this.medium_display = medium_display;
        this.artist_display = artist_display;
        this.title = title;
        this.gallery_title = gallery_title;
        this.place_of_origin = place_of_origin;
        this.credit_line = credit_line;
        this.artwork_type_title = artwork_type_title;
        this.department_title = department_title;
        this.api_link = api_link;
        this.date_display = date_display;
        this.gallery_id = gallery_id;
        this.id = id;
        this.image_id = image_id;
        this.dimensions = dimensions;
    }

    public String getImageUrl() {
        return "https://www.artic.edu/iiif/2/" + image_id + "/full/200,/0/default.jpg";
    }
    public String getMedium_display() {
        return medium_display;
    }
    public String getArtist_display() {
        return artist_display;
    }
    public String getTitle() {
        return title;
    }
    public String getGallery_title() {
        return gallery_title;
    }
    public String getPlace_of_origin() {
        return place_of_origin;
    }
    public String getCredit_line() {
        return credit_line;
    }
    public String getArtwork_type_title() {
        return artwork_type_title;
    }
    public String getDepartment_title() {
        return department_title;
    }
    public String getApi_link() {
        return api_link;
    }
    public String getDate_display() {
        return date_display;
    }
    public String getGallery_id() {
        return gallery_id;
    }
    public String getId() {
        return id;
    }

    public String getImage_id() {
        return image_id;
    }
    public String getDimensions() {
        return dimensions;
    }

}