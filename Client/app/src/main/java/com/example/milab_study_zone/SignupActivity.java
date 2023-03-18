package com.example.milab_study_zone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Find the views in the layout
        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);

        signupButton = findViewById(R.id.signup_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(v);
            }
        });
    }

    private void register(final View v) {
        // Get the username and passwords from the EditText views
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        // Validate the input fields
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not Match!", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: enter into the Node.JS server
        final SignupFetcher fetcher = new SignupFetcher(v.getContext());
        fetcher.dispatchRequest(username, password, new SignupFetcher.SignupResponseListener() {

            public void onResponse(SignupFetcher.SignupResponse response) {

                if (response.isError) {
                    Toast.makeText(v.getContext(), "Error with Signup Process", Toast.LENGTH_LONG).show();
                    return;
                }
                else if (response.success == false) {
                    Toast.makeText(v.getContext(), "", Toast.LENGTH_LONG).show();
                    return;
                }

                //if successful authentication
                Intent intent2 = new Intent(v.getContext(), loginScreen.class);
                startActivity(intent2);

            }

        });

        //Return to Log in Screen

    }
}
