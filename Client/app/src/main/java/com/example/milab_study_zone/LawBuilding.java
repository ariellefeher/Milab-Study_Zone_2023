package com.example.milab_study_zone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LawBuilding extends AppCompatActivity {

    private TextView placeNameTextView;
    private TextView placeDescriptionTextView;
    private EditText dayEditText;
    private Button dayButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law_building);

        placeNameTextView = findViewById(R.id.law_place_name);
        placeDescriptionTextView = findViewById(R.id.law_place_description);

        // Retrieve data from the marker that was clicked and update the UI elements
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String placeName = extras.getString("placeName");
            String placeDescription = extras.getString("placeDescription");

            placeNameTextView.setText(placeName);
            placeDescriptionTextView.setText(placeDescription);
        }
        Button backButtonLaw = findViewById(R.id.backButtonLaw);
        backButtonLaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dayEditText = findViewById(R.id.day_edit_text);
        dayButton = findViewById(R.id.day_button);
        dayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               reserve(v);
            }
        });
    }

    private void reserve(final View v) {
        // Get the username and passwords from the EditText views
        String location = "Law Building";

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String day = dayEditText.getText().toString();

        // Validate the input fields
        if (day.isEmpty()) {
            Toast.makeText(this, "Please fill in the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        final PostStudyZoneFetcher fetcher = new PostStudyZoneFetcher(v.getContext());
        fetcher.dispatchRequest(location,day , username, new PostStudyZoneFetcher.PostStudyZoneResListener() {

            public void onResponse(PostStudyZoneFetcher.PostStudyZoneResponse response) {

                if (response.isError) {
                    Toast.makeText(v.getContext(), "Day Unavailable!", Toast.LENGTH_LONG).show();
                    return;
                }

                //if successful authentication
                Toast.makeText(v.getContext(), "Reservation Made!", Toast.LENGTH_LONG).show();

            }

        });


    }

}