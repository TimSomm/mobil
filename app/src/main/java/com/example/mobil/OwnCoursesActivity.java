package com.example.mobil;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mobil.adapter.OwnCourseAdapter;
import com.example.mobil.model.Course;

import java.util.ArrayList;

public class OwnCoursesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Course> courses;
    private OwnCourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_courses);

        recyclerView = findViewById(R.id.ownCourseRecyclerView);
        ImageView imageView = findViewById(R.id.addCourseImageVIew);
        LinearLayout addCourse = findViewById(R.id.addCourse);

        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_NO:
                imageView.setImageResource(R.drawable.add_grey);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                imageView.setImageResource(R.drawable.add_white);
                break;
        }

        addCourse.setOnClickListener(view -> {
            navigateTo(new Intent(this, AddCourseActivity.class));
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        courses = new ArrayList<>();

        courseAdapter = new OwnCourseAdapter(this, courses);
        recyclerView.setAdapter(courseAdapter);

        initializeData();
    }

    private void navigateTo(Intent intent) {
        startActivity(intent);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initializeData() {

        courses.clear();

//        TODO firebase lekérdezés

        courses.add(new Course(
            "test",
                "test",
                "test",
                "test",
                54.99,
                "2022-04-12"
        ));

        courses.add(new Course(
                "test2",
                "test2",
                "test2",
                "test2",
                423.99,
                "2022-01-12"
        ));

        courseAdapter.notifyDataSetChanged();

    }
}