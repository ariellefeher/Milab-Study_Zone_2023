package com.example.milab_study_zone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MapActivity extends AppCompatActivity {
    private TextView usernameTextView;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Get the intent that started this activity and extract the username from it
        Intent intent = getIntent();
        String username = intent.getStringExtra("usernameEditText");

        // Find the TextViews in the layout
        usernameTextView = findViewById(R.id.username_text);

        // Set the text of the TextViews to display the username
        usernameTextView.setText(username);

    }
}