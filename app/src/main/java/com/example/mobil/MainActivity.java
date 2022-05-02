package com.example.mobil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mobil.model.FirebaseClient;

public class MainActivity extends AppCompatActivity {
    private final FirebaseClient firebaseClient = new FirebaseClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (firebaseClient.getCurrentUser() != null) {
            navigateToHome();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void navigateToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}