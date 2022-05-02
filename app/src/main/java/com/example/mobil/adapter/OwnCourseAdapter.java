package com.example.mobil.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobil.CourseDetailsActivity;
import com.example.mobil.R;
import com.example.mobil.model.Course;

import java.util.ArrayList;
import java.util.Locale;

public class OwnCourseAdapter extends RecyclerView.Adapter<OwnCourseAdapter.ViewHolder> implements Filterable {

    private ArrayList<Course> courseItemsData;
    private ArrayList<Course> courseItemsDataAll;
    private Context context;
    private int lastPosition = -1;
    private OnCourseListener onCourseListener;

    public OwnCourseAdapter(Context context, ArrayList<Course> itemsData, OnCourseListener onCourseListener) {
        this.courseItemsData = itemsData;
        this.courseItemsDataAll = itemsData;
        this.context = context;
        this.onCourseListener = onCourseListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.course_list, parent, false), onCourseListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnCourseAdapter.ViewHolder holder, int position) {
        Course current = courseItemsData.get(position);

        holder.bindTo(current);
    }

    @Override
    public int getItemCount() {
        return courseItemsData.size();
    }

    @Override
    public Filter getFilter() {
        return courseFilter;
    }

    private Filter courseFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Course> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if (charSequence == null || charSequence.length() == 0) {
                results.count = courseItemsDataAll.size();
                results.values = courseItemsDataAll;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Course course : courseItemsDataAll) {
                    if (course.getTitle().toLowerCase().trim().contains(filterPattern)) {
                        filteredList.add(course);
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            courseItemsData = (ArrayList<Course>) filterResults.values;
            notifyDataSetChanged();
        }
    };

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

