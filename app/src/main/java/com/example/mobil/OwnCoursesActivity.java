package com.example.mobil;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.mobil.adapter.OwnCourseAdapter;
import com.example.mobil.model.Course;

import java.util.ArrayList;

public class OwnCoursesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Course> courses;
    private OwnCourseAdapter courseAdapter;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_courses);

        recyclerView = findViewById(R.id.ownCourseRecyclerView);
        imageView = findViewById(R.id.addCourseImageVIew);

        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_NO:
                imageView.setImageResource(R.drawable.add_grey);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                imageView.setImageResource(R.drawable.add_white);
                break;
        }

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        courses = new ArrayList<>();

        courseAdapter = new OwnCourseAdapter(this, courses);
        recyclerView.setAdapter(courseAdapter);

        initializeData();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initializeData() {

        courses.clear();

//        TODO firebase lekérdezés

        courses.add(new Course(
            "test",
                "test",
                "test",
                new ArrayList<String>() {{
                    add("test1");
                    add("test2");
                }},
                12,
                "test",
                54.99,
                "2022-04-12"
        ));

        courses.add(new Course(
                "test2",
                "test2",
                "test2",
                new ArrayList<String>() {{
                    add("test1");
                    add("test2");
                }},
                121,
                "test2",
                423.99,
                "2022-01-12"
        ));

        courseAdapter.notifyDataSetChanged();

    }
}