package com.example.milab_study_zone;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ExistingReservations extends AppCompatActivity {
    private Button getResButton;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_reservations);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");

//       RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
//       recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getResButton = findViewById(R.id.existResButton);
        getResButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchReservations(v);
            }
        });
    }

    public void fetchReservations(final View v) {

        final ExistingReservationFetcher fetcher = new ExistingReservationFetcher(v.getContext());
        fetcher.dispatchRequest(username, password, new ExistingReservationFetcher.ExisResResponseListener() {

            public void onResponse(ExistingReservationFetcher.ExisResResponse response) {

                if (response.isError) {
                    Toast.makeText(v.getContext(), "Error Fetching User Reservations", Toast.LENGTH_LONG).show();
                    return;
                }

                //if successful authentication

            }

        });

    }
}