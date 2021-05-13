package com.example.studenttrackapp.core;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studenttrackapp.R;
import com.example.studenttrackapp.adapter.CourseAdapter;
import com.example.studenttrackapp.model.Course;
import com.example.studenttrackapp.service.CatchForegroundAppService;

import java.util.ArrayList;

public class StudentLandingActivity extends AppCompatActivity {
    RecyclerView rvCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_landing);
        ArrayList<Course> courseList = new ArrayList<>();
        courseList.add(new Course("Chinese Course", "2021-05-15 09:00", "2021-05-15 10:00", 1));
        courseList.add(new Course("Cyber Security", "2021-05-15 12:00", "2021-05-15 13:00", 2));
        courseList.add(new Course("Computer Networking", "2021-05-15 13:00", "2021-05-15 15:00", 3));
        courseList.add(new Course("System Design", "2021-05-15 15:00", "2021-05-15 17:00", 4));
        rvCourse = findViewById(R.id.rvCourseList);
        rvCourse.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        CourseAdapter mCourseAdapter = new CourseAdapter(getApplicationContext(), courseList);
        rvCourse.setAdapter(mCourseAdapter);

//        startService(new Intent(getApplicationContext(), CatchForegroundAppService.class));
    }
}
