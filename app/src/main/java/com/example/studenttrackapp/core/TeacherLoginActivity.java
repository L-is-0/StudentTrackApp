package com.example.studenttrackapp.core;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studenttrackapp.R;

public class TeacherLoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
    }

    public void onTeacherRegisterClicked(View view) {
        Intent mIntent = new Intent(this, TeacherRegisterActivity.class);
        startActivity(mIntent);
        finish();
    }

    public void onTeacherLoginClicked(View view) {
        //TODO: USE teacher login api
        Intent mIntent = new Intent(this, TeacherLandingActivity.class);
        startActivity(mIntent);
        finish();
    }
}

