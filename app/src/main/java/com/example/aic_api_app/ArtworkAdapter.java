package com.example.aic_api_app;

import static android.content.ContentValues.TAG;

import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ArtworkAdapter extends RecyclerView.Adapter<ArtworkViewHolder>{
    private final List<Artwork> artworkList;
    private final MainActivity mainActivity;
    private ImageView image;
    private Picasso picasso;
    TextView title, artistDisplay, mediumDisplay;
    public ArtworkAdapter(List<Artwork> artworkList, MainActivity mainActivity) {
        this.artworkList = artworkList;
        this.mainActivity = mainActivity;
    }
    @NonNull
    @Override
    public ArtworkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artwork_entry, parent, false);
        itemView.setOnClickListener((View.OnClickListener) mainActivity);
        itemView.setOnLongClickListener((View.OnLongClickListener) mainActivity);
        return new ArtworkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtworkViewHolder holder, int position) {
        Artwork artwork = artworkList.get(position);
        holder.medium_display.setText(artwork.getMedium_display());
        String imageURLString =
                "https://www.artic.edu/iiif/2/"  + artwork.getImage_id() + "/full/200,/0/default.jpg";
        Picasso.get()
                .load(imageURLString)
                .error(R.drawable.not_available)
               .resize(200,200)
                .centerCrop()
                .into(holder.image);
    }
    public void updateData(ArrayList<Artwork> newArtworks) {
        this.artworkList.clear();
        this.artworkList.addAll(newArtworks);
        notifyDataSetChanged();

    }


    @Override
    public int getItemCount() {
        return artworkList.size();
    }
    public Artwork getArtwork(int position) {
        return artworkList.get(position);
    }
}
