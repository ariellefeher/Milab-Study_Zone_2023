package com.example.milab_study_zone;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginScreen extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login_screen);

        usernameEditText = findViewById(R.id.username_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { fetchLogin(v); }
        });


        signupButton = findViewById(R.id.signup_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SignupActivity.class);
                startActivity(intent);
            }

        });
    }

    public void fetchLogin(final View v){
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        String url = "http://10.0.2.2:3000/login";

        // TODO: Authentication with Node.JS Server
        if (username.equals("user") && password.equals("password")) {
            Intent intent = new Intent(v.getContext(), MapActivity.class);
            startActivity(intent);

                    //If there is an authentication problem
        } else {
            Toast.makeText(loginScreen.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
        }
    }
}
