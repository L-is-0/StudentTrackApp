package com.example.studenttrackapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studenttrackapp.R;
import com.example.studenttrackapp.model.Course;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private final LayoutInflater mInflater;
    private final ArrayList<Course> mCourse;

    public CourseAdapter(Context applicationContext, ArrayList<Course> mCourse) {
        this.mInflater = LayoutInflater.from(applicationContext);
        this.mCourse = mCourse;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.course_item, parent, false);
        return new CourseViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        holder.tvCourseName.setText(mCourse.get(position).getName());
        holder.tvCourseId.setText(Integer.toString(mCourse.get(position).getId()));
        holder.tvCourseStartTime.setText(mCourse.get(position).getStartTime());
        holder.tvCourseEndTime.setText(mCourse.get(position).getEndTime());
    }

    @Override
    public int getItemCount() {
        return mCourse.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView tvCourseName, tvCourseId, tvCourseStartTime, tvCourseEndTime;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvCourseId = itemView.findViewById(R.id.tvCourseId);
            tvCourseStartTime = itemView.findViewById(R.id.tvCourseStartTime);
            tvCourseEndTime = itemView.findViewById(R.id.tvCourseEndTime);
        }
    }
}
