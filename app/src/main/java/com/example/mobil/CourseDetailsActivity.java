package com.example.mobil;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobil.model.Course;
import com.example.mobil.model.FirebaseClient;
import com.example.mobil.notification.NotificationHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.DoubleUnaryOperator;

public class CourseDetailsActivity extends AppCompatActivity {

    private final FirebaseClient firebaseClient = new FirebaseClient();
    private Course course;
    private final String currentId = firebaseClient.getCurrentUser().getUid();;
    private final ArrayList<String> usersList = new ArrayList<>();
    private NotificationHandler notificationHandler;

    private Button enrollButton;
    private Button leaveButton;
    private Button cancelButton;
    private Button deleteButton;
    private Button saveButton;

    private EditText titleEditText;
    private EditText shortDescriptionEditText;
    private EditText descriptionEditText;
    private EditText priceEditText;
    private EditText startDateEditText;
    private EditText userCountEditText;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        notificationHandler = new NotificationHandler(this);

        enrollButton = findViewById(R.id.detailsCourseEnrollButton);
        leaveButton = findViewById(R.id.detailsCourseLeaveButton);
        cancelButton = findViewById(R.id.detailsCourseCancelButton);
        deleteButton = findViewById(R.id.detailsCourseDeleteButton);
        saveButton = findViewById(R.id.detailsCourseButton);

        titleEditText = findViewById(R.id.detailsCourseTitleEditText);
        shortDescriptionEditText = findViewById(R.id.detailsCourseShortDescriptionEditText);
        descriptionEditText = findViewById(R.id.detailsCourseDescriptionEditText);
        priceEditText = findViewById(R.id.detailsCoursePriceEditText);
        startDateEditText = findViewById(R.id.detailsCourseStartDateEditText);
        userCountEditText = findViewById(R.id.detailsCourseUserCount);

        course = getIntent().getParcelableExtra("course");

        initData();
        refreshButtons();

        cancelButton.setOnClickListener(view -> {
            finish();
        });

        deleteButton.setOnClickListener(view -> {
            firebaseClient.deleteCourse(course);
            Toast.makeText(this, "Sikeres törlés!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, OwnCoursesActivity.class));
            notificationHandler.send(course.getTitle() + " című kurzus törölve lett!");
        });

        saveButton.setOnClickListener(view -> {
            String title = titleEditText.getText().toString();
            String shortDesc = shortDescriptionEditText.getText().toString();
            String desc = descriptionEditText.getText().toString();
            String price = priceEditText.getText().toString();
            String startDate = startDateEditText.getText().toString();

            if (title.equals("") || shortDesc.equals("") || desc.equals("") || startDate.equals("") || price.equals("")) {
                Toast.makeText(CourseDetailsActivity.this, "Minden mezőt ki kell tölteni", Toast.LENGTH_LONG).show();
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

            course.setTitle(title);
            course.setShort_description(shortDesc);
            course.setDescription(desc);
            course.setPrice(Double.parseDouble(price));
            course.setStart_date(startDate);

            firebaseClient.modifyCourse(course);
            Toast.makeText(this, "Sikeres frissítés!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, HomeActivity.class));
        });

        enrollButton.setOnClickListener(view -> {
            usersList.add(currentId);
            course.setUsersList(usersList);

            firebaseClient.modifyCourse(course);
            Toast.makeText(this, "Sikeres jelentkezés!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, HomeActivity.class));
        });

        leaveButton.setOnClickListener(view -> {
            usersList.remove(currentId);
            course.setUsersList(usersList);
            firebaseClient.modifyCourse(course);
            Toast.makeText(this, "Sikeres kilépés!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, HomeActivity.class));
        });
    }

    private void refreshButtons() {
        if (course.getUsersList() != null && !course.getUsersList().contains(currentId)) {
            leaveButton.setVisibility(View.GONE);
            enrollButton.setVisibility(View.VISIBLE);
        } else {
            enrollButton.setVisibility(View.GONE);
            leaveButton.setVisibility(View.VISIBLE);
        }

        if (!currentId.equals(course.getOwnerId())) {
            deleteButton.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);
        } else {
            leaveButton.setVisibility(View.GONE);
            enrollButton.setVisibility(View.GONE);
        }
    }

    private void initData() {
        titleEditText.setText(course.getTitle());
        shortDescriptionEditText.setText(course.getShort_description());
        descriptionEditText.setText(course.getDescription());
        priceEditText.setText(String.valueOf(course.getPrice()));
        startDateEditText.setText(course.getStart_date());
        userCountEditText.setText(String.valueOf(course.getUserCount()));

        if (!currentId.equals(course.getOwnerId())) {
            titleEditText.setFocusable(false);
            shortDescriptionEditText.setFocusable(false);
            descriptionEditText.setFocusable(false);
            priceEditText.setFocusable(false);
            startDateEditText.setFocusable(false);
        }
    }
}