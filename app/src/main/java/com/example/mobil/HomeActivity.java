package com.example.mobil;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobil.model.FirebaseClient;
import com.example.mobil.model.User;

public class HomeActivity extends AppCompatActivity {
    private static final String LOG_TAG = HomeActivity.class.getName();
    private final FirebaseClient firebaseClient = new FirebaseClient();
    private User user;

    private TextView welcomeTextView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        welcomeTextView = findViewById(R.id.welcomeTextView);
        Button profileButton = findViewById(R.id.profile);
        Button logoutButton = findViewById(R.id.logout);
        Button ownCourses = findViewById(R.id.ownCourses);

        firebaseClient.getUser(firebaseClient.getCurrentUser().getUid()).addOnSuccessListener(documentSnapshot -> {
            user = documentSnapshot.toObject(User.class);
            assert user != null;
            welcomeTextView.setText(getResources().getString(R.string.welcome) + " " + user.getFormattedFullName());
        });

        profileButton.setOnClickListener(view -> {
            navigateTo(new Intent(this, ProfileActivity.class));
        });

        logoutButton.setOnClickListener(view -> {
            firebaseClient.logOut();
            navigateTo(new Intent(this, MainActivity.class));
        });

        ownCourses.setOnClickListener(view -> {
            navigateTo(new Intent(this, OwnCoursesActivity.class));
        });
    }

    private void navigateTo(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }
}