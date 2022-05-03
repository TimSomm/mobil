package com.example.mobil;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobil.adapter.EnrolledCourseAdapter;
import com.example.mobil.model.FirebaseClient;
import com.example.mobil.model.User;
import com.example.mobil.notification.NotificationHandler;
import com.example.mobil.notification.NotificationJobService;

public class HomeActivity extends AppCompatActivity {
    private final FirebaseClient firebaseClient = new FirebaseClient();
    private static final String PREF_KEY = HomeActivity.class.getPackage().toString();
    private User user;
    private NotificationHandler notificationHandler;

    private TextView welcomeTextView;
    private SharedPreferences preferences;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (firebaseClient.getCurrentUser() == null) {
            navigateTo(new Intent(this, MainActivity.class));
        } else {
            setContentView(R.layout.activity_home);
            preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
            notificationHandler = new NotificationHandler(this);

            welcomeTextView = findViewById(R.id.welcomeTextView);
            Button profileButton = findViewById(R.id.profile);
            Button logoutButton = findViewById(R.id.logout);
            Button ownCourses = findViewById(R.id.ownCourses);
            Button enrolledCourses = findViewById(R.id.myCourses);
            Button allCourses = findViewById(R.id.allCourse);

            firebaseClient.getUser(firebaseClient.getCurrentUser().getUid()).addOnSuccessListener(documentSnapshot -> {
                user = documentSnapshot.toObject(User.class);
                assert user != null;
                welcomeTextView.setText(getResources().getString(R.string.welcome) + " " + user.getFormattedFullName());
            });

            profileButton.setOnClickListener(view -> {
                navigateTo(new Intent(this, ProfileActivity.class));
            });

            logoutButton.setOnClickListener(view -> {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("email", user.getEmail());
                editor.apply();
                firebaseClient.logOut();
                navigateTo(new Intent(this, MainActivity.class));
            });

            ownCourses.setOnClickListener(view -> {
                navigateTo(new Intent(this, OwnCoursesActivity.class));
            });

            enrolledCourses.setOnClickListener(view -> {
                navigateTo(new Intent(this, EnrolledCoursesActivity.class));
            });

            allCourses.setOnClickListener(view -> {
                navigateTo(new Intent(this, AllCoursesActivity.class));
            });
        }
    }

    private void navigateTo(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notificationHandler.send("Siess vissza tanulni!");
    }
}