package com.example.mobil.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobil.R;
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
            String password = passwordEditText.getText().toString();
            String firstName = firstNameEditText.getText().toString();
            String lastName = lastNameEditText.getText().toString();
            String email = emailEditText.getText().toString();

            if (firstName.equals("") || lastName.equals("") || email.equals("")) {
                Toast.makeText(ProfileActivity.this, "Minden mezőt ki kell tölteni, a jelszó kivételével!", Toast.LENGTH_LONG).show();
                return;
            }

            if (password.length() < 6 && !password.equals("")) {
                passwordEditText.setError("A jelszó legalább 6 hosszú kell legyen");
                return;
            }

            if (!email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])")) {
                emailEditText.setError("Nem megfelelő email formátum!");
                return;
            }

            if (password.equals("")) {
                password = user.getPassword();
            }

            User newUser = new User(firstName, lastName, email, password);
            newUser.setId(user.getId());
            newUser.setCreated_at(user.getCreated_at());

            firebaseClient.modifyUser(newUser, user.getEmail(), user.getPassword());
            Toast.makeText(ProfileActivity.this, "Sikeres frissítés!", Toast.LENGTH_LONG).show();
            navigateTo(new Intent(this, HomeActivity.class));
        });

        deleteButton.setOnClickListener(view -> {
            firebaseClient.deleteCourseForUser(user);
            firebaseClient.deleteUser(user);
            firebaseClient.deleteAuthUser(firebaseClient.getCurrentUser());
            firebaseClient.logOut();
            Toast.makeText(ProfileActivity.this, "Sikeres törlés!", Toast.LENGTH_LONG).show();
            navigateTo(new Intent(this, MainActivity.class));
        });

        firebaseClient.getUser(firebaseClient.getCurrentUser().getUid()).addOnSuccessListener(documentSnapshot -> {
            user = documentSnapshot.toObject(User.class);
            assert user != null;
            initData(user);
        });
    }

    @Override
    public void onBackPressed() {
        navigateTo(new Intent(this, HomeActivity.class));
    }

    private void navigateTo(Intent intent) {
        startActivity(intent);
    }

    private void initData(User user) {
        firstNameEditText.setText(user.getFirstName());
        lastNameEditText.setText(user.getLastName());
        emailEditText.setText(user.getEmail());
    }
}