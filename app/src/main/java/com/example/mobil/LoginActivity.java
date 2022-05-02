package com.example.mobil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobil.model.FirebaseClient;

public class LoginActivity extends AppCompatActivity {

    private final FirebaseClient firebaseClient = new FirebaseClient();

    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.loginEmailInput);
        passwordEditText = findViewById(R.id.loginPasswordInput);
        Button cancelButton = findViewById(R.id.cancelButton3);
        Button loginButton = findViewById(R.id.loginButton);

        cancelButton.setOnClickListener(view -> {
            finish();
        });

        loginButton.setOnClickListener(view -> {
            login();
        });
    }

    public void login() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        firebaseClient.loginWithEmailAndPasword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(LoginActivity.this, "Sikeres bejelentkezés!", Toast.LENGTH_LONG).show();
                navigateTo(new Intent(this, HomeActivity.class));
            } else {
                Toast.makeText(LoginActivity.this, "Sikertelen bejelentkezés!", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void navigateTo(Intent intent) {
        startActivity(intent);
    }
}