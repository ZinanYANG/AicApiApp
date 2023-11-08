package com.example.aic_api_app;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class ArtworkViewHolder extends RecyclerView.ViewHolder{

    TextView medium_display;
    ImageView image;


    public ArtworkViewHolder(@NonNull View itemView) {
        super(itemView);
        medium_display = itemView.findViewById(R.id.medium_display);
        image = itemView.findViewById(R.id.image);
    }
}
