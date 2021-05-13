package com.example.studenttrackapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.studenttrackapp.core.StudentLoginActivity;
import com.example.studenttrackapp.core.TeacherLoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStudentEntryClicked(View view) {
        Intent mIntent = new Intent(this, StudentLoginActivity.class);
        startActivity(mIntent);
    }

    public void onTeacherEntryClicked(View view) {
        Intent mIntent = new Intent(this, TeacherLoginActivity.class);
        startActivity(mIntent);
    }
}