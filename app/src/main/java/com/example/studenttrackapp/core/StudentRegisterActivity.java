package com.example.studenttrackapp.core;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studenttrackapp.R;

public class StudentRegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
    }

    public void onStudentLoginClicked(View view) {
        Intent mIntent = new Intent(this, StudentLoginActivity.class);
        startActivity(mIntent);
        finish();
    }

    public void onStudentRegisterClicked(View view) {
        //TODO: USE student register api
    }
}
