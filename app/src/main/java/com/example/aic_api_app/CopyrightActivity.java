package com.example.aic_api_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CopyrightActivity extends AppCompatActivity {

    private TextView artLink;
    private TextView fontLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copyright);

        artLink = findViewById(R.id.artLink);
        fontLink = findViewById(R.id.fontLink);
    }

    public void returnMain(View v) {
        Intent intent = new Intent(CopyrightActivity.this, MainActivity.class);
        startActivity(intent);
    }
}