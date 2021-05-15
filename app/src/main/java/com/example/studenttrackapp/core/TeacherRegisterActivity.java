package com.example.studenttrackapp.core;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studenttrackapp.R;
import com.example.studenttrackapp.net.Api;
import com.example.studenttrackapp.net.ApiCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class TeacherRegisterActivity extends AppCompatActivity {
    EditText etTeacherName, etTeacherPsw, etTeacherNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_register);
        etTeacherName = findViewById(R.id.etTeacherName);
        etTeacherPsw = findViewById(R.id.etTeacherPsw);
        etTeacherNo = findViewById(R.id.etTeacherNo);
    }

    public void onTeacherLoginClicked(View view) {
        Intent mIntent = new Intent(this, TeacherLoginActivity.class);
        startActivity(mIntent);
    }

    public void onTeacherRegisterClicked(View view) {
        String name = etTeacherName.getText().toString();
        String psw = etTeacherPsw.getText().toString();
        String teacherNo = etTeacherNo.getText().toString();

        Api.getInstance().teacherRegister(name, psw, teacherNo, new ApiCallback() {
            @Override
            public void getJsonArray(String response) throws JSONException {
                if (response != null) {
                    Log.d(TAG, "teacherRegister response is " + response);
                    JSONObject object = new JSONObject(response);
                    if (object.has("code") && object.getInt("code") == 200) {
                        Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                        teacherRegister();
                    } else {
                        Toast.makeText(getApplicationContext(), "网络问题", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onTimeout(String apiEndpoint) {

            }

            @Override
            public void onFailure(String apiEndpoint, int statuscode) {

            }
        });
    }

    public void teacherRegister() {
        Intent mIntent = new Intent(this, TeacherLoginActivity.class);
        startActivity(mIntent);
        finish();
    }
}
