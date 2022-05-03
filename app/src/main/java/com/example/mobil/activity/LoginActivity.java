package com.example.mobil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobil.model.FirebaseClient;

public class LoginActivity extends AppCompatActivity {
    private static final String PREF_KEY = HomeActivity.class.getPackage().toString();
    private final FirebaseClient firebaseClient = new FirebaseClient();

    private EditText emailEditText;
    private EditText passwordEditText;
    private CheckBox rememberMeCheckBox;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        editor = preferences.edit();

        String email = preferences.getString("email", "");
        boolean rememberMe = preferences.getBoolean("remember_me", false);

        emailEditText = findViewById(R.id.loginEmailInput);
        passwordEditText = findViewById(R.id.loginPasswordInput);
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox);

        if (rememberMe) {
            emailEditText.setText(email);
            rememberMeCheckBox.setChecked(true);
        }

        Button cancelButton = findViewById(R.id.cancelButton3);
        Button loginButton = findViewById(R.id.loginButton);

        cancelButton.setOnClickListener(view -> {
            finish();
        });

        loginButton.setOnClickListener(view -> {
            login();
        });
    }

    private void login() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        editor.putBoolean("remember_me", rememberMeCheckBox.isChecked());
        editor.apply();

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