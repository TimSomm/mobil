package com.example.mobil.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobil.R;
import com.example.mobil.model.Course;
import com.example.mobil.model.FirebaseClient;
import com.example.mobil.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddCourseActivity extends AppCompatActivity {

    private final FirebaseClient firebaseClient = new FirebaseClient();

    private User user;

    private EditText titleEditText;
    private EditText shortDescriptionEditText;
    private EditText descriptionEditText;
    private EditText priceEditText;
    private EditText startDateEditText;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        Button cancelButton = findViewById(R.id.detailsCourseCancelButton);
        Button saveButton = findViewById(R.id.detailsCourseButton);

        titleEditText = findViewById(R.id.detailsCourseTitleEditText);
        shortDescriptionEditText = findViewById(R.id.detailsCourseShortDescriptionEditText);
        descriptionEditText = findViewById(R.id.detailsCourseDescriptionEditText);
        priceEditText = findViewById(R.id.detailsCoursePriceEditText);
        startDateEditText = findViewById(R.id.detailsCourseStartDateEditText);

        cancelButton.setOnClickListener(view -> {
            finish();
        });

        saveButton.setOnClickListener(view -> {
            String title = titleEditText.getText().toString();
            String shortDescription = shortDescriptionEditText.getText().toString();
            String description = descriptionEditText.getText().toString();
            String price = priceEditText.getText().toString();
            String startDate = startDateEditText.getText().toString();

            if (title.equals("") || shortDescription.equals("") || description.equals("") || price.equals("") || startDate.equals("")) {
                Toast.makeText(AddCourseActivity.this, "Minden mezőt ki kell tölteni", Toast.LENGTH_LONG).show();
                return;
            }

            if (!startDate.matches("^\\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")) {
                startDateEditText.setError("Nem megfelelő formátum (yyyy-mm-dd)");
                return;
            }

            Date start = null;

            try {
                start = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Date now = new Date(System.currentTimeMillis());

            if (start == null || start.before(now)) {
                Toast.makeText(this, "A mai nap előtti dátumot nem adhat meg kezdésnek", Toast.LENGTH_LONG).show();
                return;
            }

            Course course = new Course(
                    title,
                    description,
                    shortDescription,
                    firebaseClient.getCurrentUser().getUid(),
                    user.getFormattedFullName(),
                    Double.parseDouble(price),
                    startDate
            );

            course.setOwnerId(user.getId());
            course.setUsersList(new ArrayList<>());

            firebaseClient.saveCourse(course);
            Toast.makeText(this, "Sikeres hozzáadás", Toast.LENGTH_LONG).show();
            navigateTo(new Intent(this, OwnCoursesActivity.class));
        });

        firebaseClient.getUser(firebaseClient.getCurrentUser().getUid()).addOnSuccessListener(documentSnapshot -> {
            user = documentSnapshot.toObject(User.class);
        });
    }

    private void navigateTo(Intent intent) {
        startActivity(intent);
    }
}