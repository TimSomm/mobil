package com.example.mobil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.mobil.model.FirebaseClient;

public class MainActivity extends AppCompatActivity {
    private final FirebaseClient firebaseClient = new FirebaseClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (firebaseClient.getCurrentUser() != null) {
            navigateTo(new Intent(this, HomeActivity.class));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button registerButton = findViewById(R.id.register);
        Button loginButton = findViewById(R.id.login);

        registerButton.setOnClickListener(view -> {
            navigateTo(new Intent(this, RegisterActivity.class));
        });

        loginButton.setOnClickListener(view -> {
            navigateTo(new Intent(this, LoginActivity.class));
        });
    }

    private void navigateTo(Intent intent) {
        startActivity(intent);
    }
}