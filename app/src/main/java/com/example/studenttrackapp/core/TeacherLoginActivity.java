package com.example.studenttrackapp.core;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studenttrackapp.R;
import com.example.studenttrackapp.net.Api;
import com.example.studenttrackapp.net.ApiCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class TeacherLoginActivity extends AppCompatActivity {
    EditText etTeacherName, etTeacherPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        etTeacherName = findViewById(R.id.etTeacherName);
        etTeacherPsw = findViewById(R.id.etTeacherPsw);

    }

    public void onTeacherRegisterClicked(View view) {
        Intent mIntent = new Intent(this, TeacherRegisterActivity.class);
        startActivity(mIntent);
        finish();
    }

    public void onTeacherLoginClicked(View view) {
        String name = etTeacherName.getText().toString();
        String psw = etTeacherPsw.getText().toString();
        Api.getInstance().teacherLogin(name, psw, new ApiCallback() {
            @Override
            public void getJsonArray(String response) throws JSONException {
                if (response != null) {
                    JSONObject object = new JSONObject(response);
                    if (object.has("code") && object.getInt("code") == 200) {
                        Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                        if (object.has("data") && !object.isNull("data")) {
                            JSONObject teacherObject = object.getJSONObject("data");
                            String id = teacherObject.getString("id");
                            String week = teacherObject.getString("week");
                            String name = teacherObject.getString("name");
                            String stuId = teacherObject.getString("stuId");
                            String classroom = teacherObject.getString("classroom");
                            String techId = teacherObject.getString("techId");
                            String start1 = teacherObject.getString("start1");
                            String start2 = teacherObject.getString("start2");
                            String end1 = teacherObject.getString("end1");
                            String end2 = teacherObject.getString("end2");
                            storeTeacherInfo(id, week, name, stuId, classroom, techId, start1, start2, end1, end2);
                            teacherLogin();
                        } else {
                            Toast.makeText(getApplicationContext(), "暂无绑定信息", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "登陆失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onTimeout(String apiEndpoint) {
                Toast.makeText(getApplicationContext(), "接口请求超时", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String apiEndpoint, int statuscode) {
                Toast.makeText(getApplicationContext(), "接口请求失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void storeTeacherInfo(String id, String week, String name, String stuId, String classroom, String techId, String start1, String start2, String end1, String end2) {
        SharedPreferences teacherInfo = getSharedPreferences("teacherInfo", 0);
        SharedPreferences.Editor editor = teacherInfo.edit();
        editor.putString("tid", id);
        editor.putString("week", week);
        editor.putString("name", name);
        editor.putString("stuId", stuId);
        editor.putString("classroom", classroom);
        editor.putString("techId", techId);
        editor.putString("start1", start1);
        editor.putString("start2", start2);
        editor.putString("end1", end1);
        editor.putString("end2", end2);
        editor.commit();
    }

    private void teacherLogin() {
        Intent mIntent = new Intent(this, TeacherLandingActivity.class);
        startActivity(mIntent);
        finish();
    }
}

