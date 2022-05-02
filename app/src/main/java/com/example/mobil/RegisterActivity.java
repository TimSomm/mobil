package com.example.mobil;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobil.model.FirebaseClient;
import com.example.mobil.model.User;
import com.example.mobil.model.UserPermissions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegisterActivity.class.getName();

    private final FirebaseClient firebaseClient = new FirebaseClient();

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordAgainEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (firebaseClient.getCurrentUser() != null) {
            navigateToHome();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstNameEditText = findViewById(R.id.registerFirstNameInput);
        lastNameEditText = findViewById(R.id.registerLastNameInput);
        emailEditText = findViewById(R.id.registerEmailInput);
        passwordEditText = findViewById(R.id.registerPasswordInput);
        passwordAgainEditText = findViewById(R.id.registerPasswordAgainInput);
    }

    public void register(View view) {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String passwordAgain = passwordAgainEditText.getText().toString();


        if (password.length() < 6) {
            passwordEditText.setError("A jelszónak legalább 6 karakterből kell állnia");
            return;
        }

        if (!password.equals(passwordAgain)) {
            passwordEditText.setError("A jelszavak nem egyeznek meg!");
            return;
        }

        if (!email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
            emailEditText.setError("Nem megfelelő email formátum!");
            return;
        }

        User user = new User(firstName, lastName, email, password, UserPermissions.ROLE_NORMAL);

        firebaseClient.registerWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                firebaseClient.saveUser(user, firebaseClient.getCurrentUser());
                Toast.makeText(RegisterActivity.this, "Sikeres regisztráció!", Toast.LENGTH_LONG).show();
                navigateToHome();
            } else {
                Toast.makeText(RegisterActivity.this, "Ilyen e-mail címmel már van regisztrálva fiók", Toast.LENGTH_LONG).show();
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