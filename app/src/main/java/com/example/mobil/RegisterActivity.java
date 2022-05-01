package com.example.mobil;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private FirebaseAuth mAuth;

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordAgainEditText;
    private DatePicker birthDateDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        firstNameEditText = findViewById(R.id.registerFirstNameInput);
        lastNameEditText = findViewById(R.id.registerLastNameInput);
        emailEditText = findViewById(R.id.registerEmailInput);
        passwordEditText = findViewById(R.id.registerPasswordInput);
        passwordAgainEditText = findViewById(R.id.registerPasswordAgainInput);
        birthDateDatePicker = findViewById(R.id.registerBirthDateInput);
    }

    public void register(View view) {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String passwordAgain = passwordAgainEditText.getText().toString();
        Calendar calendar = new GregorianCalendar(birthDateDatePicker.getYear(), birthDateDatePicker.getMonth(), birthDateDatePicker.getDayOfMonth());
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String birthDate = dateFormat.format(calendar.getTime());

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
              Log.i(LOG_TAG, "success");
            } else {
                Log.i(LOG_TAG, "error");
            }
        });
    }
}