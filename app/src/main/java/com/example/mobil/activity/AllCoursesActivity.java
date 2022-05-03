package com.example.mobil.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.mobil.R;
import com.example.mobil.adapter.AllCoursesAdapter;
import com.example.mobil.model.Course;
import com.example.mobil.model.FirebaseClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class AllCoursesActivity extends AppCompatActivity implements AllCoursesAdapter.OnCourseListener{

    private final FirebaseClient firebaseClient = new FirebaseClient();

    private RecyclerView recyclerView;
    private ArrayList<Course> courses;
    private AllCoursesAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_courses);

        recyclerView = findViewById(R.id.allCourseRecyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        courses = new ArrayList<>();

        courseAdapter = new AllCoursesAdapter(this, courses, this);
        recyclerView.setAdapter(courseAdapter);

        initializeData();
    }

    @Override
    public void onBackPressed() {
        navigateTo(new Intent(this, HomeActivity.class));
    }

    private void navigateTo(Intent intent) {
        startActivity(intent);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initializeData() {

        courses.clear();

        firebaseClient.getCollection(FirebaseClient.COURSE_COLLECTION)
                .orderBy("title")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document: task.getResult()) {

                                Course course = new Course(
                                        document.getString("title"),
                                        document.getString("description"),
                                        document.getString("short_description"),
                                        document.getString("ownerId"),
                                        document.getString("ownerName"),
                                        Objects.requireNonNull(document.getDouble("price")),
                                        document.getString("start_date")
                                );

                                ArrayList<String> usersList = (ArrayList<String>) document.get("usersList");

                                course.setUsersList(usersList);
                                course.setCreated_at(document.getString("created_at"));
                                course.setUserCount(Objects.requireNonNull(document.getLong("userCount")).intValue());
                                course.setUpdated_at(document.getString("updated_at"));
                                course.setId(document.getString("id"));

                                courses.add(course);
                                courseAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    @Override
    public void onCourseClick(int position) {
        Intent intent = new Intent(this, CourseDetailsActivity.class);
        intent.putExtra("course", courses.get(position));
        startActivity(intent);
    }
}