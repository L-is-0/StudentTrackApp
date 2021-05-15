package com.example.studenttrackapp.core;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

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
    TextView tvCourseId, tvCourseName, tvClassRoom, tvStudentId, tvTeacherId, tvStart1, tvEnd1, tvStart2, tvEnd2;
    String id, week, name, stuId, classroom, techId, start1, start2, end1, end2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_landing);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        tvCourseId = findViewById(R.id.tvCourseId);
        tvCourseName = findViewById(R.id.tvCourseName);
        tvClassRoom = findViewById(R.id.tvClassRoom);
        tvStudentId = findViewById(R.id.tvStuId);
        tvTeacherId = findViewById(R.id.tvTeachId);
        tvStart1 = findViewById(R.id.tvStart1);
        tvStart2 = findViewById(R.id.tvStart2);
        tvEnd1 = findViewById(R.id.tvEnd1);
        tvEnd2 = findViewById(R.id.tvEnd2);

        getStudentLoginData();

        startService(new Intent(getApplicationContext(), CatchForegroundAppService.class));
    }

    private void getStudentLoginData() {
        SharedPreferences studentInfo = getSharedPreferences("studentInfo", 0);
        id = studentInfo.getString("tid", null);
        week = studentInfo.getString("week", null);
        name = studentInfo.getString("name", null);
        stuId = studentInfo.getString("stuId", null);
        classroom = studentInfo.getString("classroom", null);
        techId = studentInfo.getString("techId", null);
        start1 = studentInfo.getString("start1", null);
        start2 = studentInfo.getString("start2", null);
        end1 = studentInfo.getString("end1", null);
        end2 = studentInfo.getString("end2", null);

        tvCourseId.setText(id);
        tvCourseName.setText(name);
        tvClassRoom.setText(classroom);
        tvStudentId.setText(stuId);
        tvTeacherId.setText(techId);
        tvStart1.setText(start1);
        tvStart2.setText(start2);
        tvEnd1.setText(end1);
        tvEnd2.setText(end2);
    }

}
