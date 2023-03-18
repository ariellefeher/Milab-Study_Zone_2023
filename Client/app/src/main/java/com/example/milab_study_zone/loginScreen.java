package com.example.milab_study_zone;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.ProgressDialog;



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
            public void onClick(View v) {
                fetchLogin(v);
//                Intent intent2 = new Intent(v.getContext(), MapActivity.class);
//                startActivity(intent2);

            }
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

        final LoginFetcher fetcher = new LoginFetcher(v.getContext());
        fetcher.dispatchRequest(username, password, new LoginFetcher.LoginResponseListener() {

            public void onResponse(LoginFetcher.LoginResponse response) {

                if (response.isError) {
                    Toast.makeText(v.getContext(), "Error with Authentication Process", Toast.LENGTH_LONG).show();
                    return;
                }
                else if (response.success == false) {
                    Toast.makeText(v.getContext(), "Invalid username or password", Toast.LENGTH_LONG).show();
                    return;
                }

                //if successful authentication
                    Intent intent2 = new Intent(v.getContext(), MapActivity.class);
                    startActivity(intent2);

            }

        });

    }
}
