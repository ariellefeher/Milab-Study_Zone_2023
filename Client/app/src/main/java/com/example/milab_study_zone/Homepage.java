package com.example.milab_study_zone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Homepage extends AppCompatActivity {

    private Button createReservationButton;
    private Button viewReservationsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        // initialize the buttons
        createReservationButton = findViewById(R.id.newReservationButton);
        viewReservationsButton = findViewById(R.id.existingReservationsButton);

        // set click listeners for the buttons
        createReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, MapActivity.class);
                startActivity(intent);
            }
        });

        viewReservationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, ExistingReservations.class);
                startActivity(intent);
            }
        });
    }
}