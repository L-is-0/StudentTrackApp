package com.example.studenttrackapp.core;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studenttrackapp.R;
import com.example.studenttrackapp.adapter.CourseAdapter;
import com.example.studenttrackapp.model.Course;

import java.util.ArrayList;

public class TeacherLandingActivity extends AppCompatActivity {
    RecyclerView rvCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_landing);

//        startService(new Intent(getApplicationContext(), CatchForegroundAppService.class));
    }
}
