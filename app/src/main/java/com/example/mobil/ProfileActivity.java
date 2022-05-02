package com.example.mobil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mobil.model.FirebaseClient;
import com.example.mobil.model.User;

public class ProfileActivity extends AppCompatActivity {

    private final FirebaseClient firebaseClient = new FirebaseClient();

    private User user;

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firstNameEditText = findViewById(R.id.profileFirstNameInput);
        lastNameEditText = findViewById(R.id.lastNameProfileInput);
        emailEditText = findViewById(R.id.emailProfileInput);
        passwordEditText = findViewById(R.id.passwordProfileInput);
        Button cancelButton = findViewById(R.id.cancelProfile);

        cancelButton.setOnClickListener(view -> {
            finish();
        });

        firebaseClient.getUser(firebaseClient.getCurrentUser().getUid()).addOnSuccessListener(documentSnapshot -> {
            user = documentSnapshot.toObject(User.class);
            assert user != null;
            firstNameEditText.setText(user.getFirstName());
            lastNameEditText.setText(user.getLastName());
            emailEditText.setText(user.getEmail());
        });
    }
}