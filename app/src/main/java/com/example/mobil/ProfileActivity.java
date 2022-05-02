package com.example.mobil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        Button saveButton = findViewById(R.id.saveProfile);
        Button deleteButton = findViewById(R.id.deleteProfile);

        cancelButton.setOnClickListener(view -> {
            finish();
        });

        saveButton.setOnClickListener(view -> {
            User newUser = new User(firstNameEditText.getText().toString(), lastNameEditText.getText().toString(), emailEditText.getText().toString(), passwordEditText.getText().toString());
            newUser.setId(user.getId());
            newUser.setCreated_at(user.getCreated_at());

            firebaseClient.saveUser(newUser);
            Toast.makeText(ProfileActivity.this, "Sikeres frissítés!", Toast.LENGTH_LONG).show();
            navigateUpTo(new Intent(this, HomeActivity.class));
        });

        deleteButton.setOnClickListener(view -> {
            firebaseClient.deleteUser(user);
            firebaseClient.deleteAuthUser(firebaseClient.getCurrentUser());
            firebaseClient.logOut();
            Toast.makeText(ProfileActivity.this, "Sikeres törlés!", Toast.LENGTH_LONG).show();
            navigateUpTo(new Intent(this, MainActivity.class));
        });

        firebaseClient.getUser(firebaseClient.getCurrentUser().getUid()).addOnSuccessListener(documentSnapshot -> {
            user = documentSnapshot.toObject(User.class);
            assert user != null;
            initData(user);
        });
    }

    private void initData(User user) {
        firstNameEditText.setText(user.getFirstName());
        lastNameEditText.setText(user.getLastName());
        emailEditText.setText(user.getEmail());
    }
}