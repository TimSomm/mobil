package com.example.mobil.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobil.R;
import com.example.mobil.model.Course;

import java.util.ArrayList;

public class AllCoursesAdapter extends RecyclerView.Adapter<AllCoursesAdapter.ViewHolder> {

    private ArrayList<Course> courseItemsData;
    private Context context;
    private OnCourseListener onCourseListener;

    public AllCoursesAdapter(Context context, ArrayList<Course> itemsData, OnCourseListener onCourseListener) {
        this.courseItemsData = itemsData;
        this.context = context;
        this.onCourseListener = onCourseListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.course_list, parent, false), onCourseListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course current = courseItemsData.get(position);

        holder.bindTo(current);
    }

    @Override
    public int getItemCount() {
        return courseItemsData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView titleTextView;
        private final TextView descriptionTextView;
        private final TextView ownerTextView;
        private final TextView priceTextView;
        private final OnCourseListener onCourseListener;

        public ViewHolder(@NonNull View itemView, OnCourseListener onCourseListener) {
            super(itemView);

            this.onCourseListener = onCourseListener;

            itemView.setOnClickListener(this);

            titleTextView = itemView.findViewById(R.id.courseTitle);
            descriptionTextView = itemView.findViewById(R.id.courseDescription);
            ownerTextView = itemView.findViewById(R.id.courseOwner);
            priceTextView = itemView.findViewById(R.id.coursePrice);
        }

        @SuppressLint("SetTextI18n")
        public void bindTo(Course course) {
            titleTextView.setText(course.getTitle());
            descriptionTextView.setText(course.getShort_description());
            ownerTextView.setText("Oktató: " + course.getOwnerName());
            priceTextView.setText("Ár: " + course.getPrice() + "$");
        }

        @Override
        public void onClick(View view) {
            onCourseListener.onCourseClick(getAdapterPosition());
        }
    }

    public interface OnCourseListener {
        void onCourseClick(int position);
    }
}

