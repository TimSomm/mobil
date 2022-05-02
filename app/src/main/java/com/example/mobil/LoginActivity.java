package com.example.mobil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    }

    public void login(View view) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        firebaseClient.loginWithEmailAndPasword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(LoginActivity.this, "Sikeres bejelentkezés!", Toast.LENGTH_LONG).show();
                navigateToHome();
            } else {
                Toast.makeText(LoginActivity.this, "Sikertelen bejelentkezés!", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void navigateToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void cancel(View view) {
        finish();
    }
}