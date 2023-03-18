package com.example.milab_study_zone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EntreBuilding extends AppCompatActivity {

    private TextView placeNameTextView;
    private TextView placeDescriptionTextView;
    private EditText dayEditText;
    private Button dayButton;
    private Button getResButton;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entre_building);

        placeNameTextView = findViewById(R.id.law_place_name);
        placeDescriptionTextView = findViewById(R.id.law_place_description);

        placeNameTextView.setText("Entrepreneurship Building");
        placeDescriptionTextView.setText("");

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

        recyclerView = (RecyclerView) findViewById(R.id.buildingRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getResButton = findViewById(R.id.existResButton);
        getResButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchReservations(v);
            }
        });

        fetchReservations(getResButton); // automatically fetch reservations on activity creation
    }

    private void reserve(final View v) {
        // Get the username and passwords from the EditText views
        String location = "Entrepreneurship Building";

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

    public void fetchReservations(final View v) {
        final GetStudyZoneFetcher fetcher = new GetStudyZoneFetcher(v.getContext());
        fetcher.dispatchRequest("Entrepreneurship Building", new GetStudyZoneFetcher.GetStudyZoneResListener() {

            public void onResponse(GetStudyZoneFetcher.GetStudyZoneResponse response) {

                if (response.isError) {
                    Toast.makeText(v.getContext(), "Error Fetching User Reservations", Toast.LENGTH_LONG).show();
                    return;
                }

                //if successful
                BuildingAdapter adapter = new BuildingAdapter(response.study_reservations);
                recyclerView.setAdapter(adapter);

            }

        });
    }

}