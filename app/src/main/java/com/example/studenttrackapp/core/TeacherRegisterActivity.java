package com.example.studenttrackapp.core;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studenttrackapp.R;

public class TeacherRegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_register);
    }

    public void onTeacherLoginClicked(View view) {
        Intent mIntent = new Intent(this, TeacherLoginActivity.class);
        startActivity(mIntent);
    }

    public void onTeacherRegisterClicked(View view) {
        //TODO: USE register api
    }
}
