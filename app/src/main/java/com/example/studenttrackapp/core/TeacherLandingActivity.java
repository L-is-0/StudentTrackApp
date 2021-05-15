package com.example.studenttrackapp.core;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studenttrackapp.R;
import com.example.studenttrackapp.adapter.CourseAdapter;
import com.example.studenttrackapp.adapter.StudentAppAdapter;
import com.example.studenttrackapp.model.StudentAppInfo;
import com.example.studenttrackapp.net.Api;
import com.example.studenttrackapp.net.ApiCallback;
import com.example.studenttrackapp.service.GetStudentInfoService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TeacherLandingActivity extends AppCompatActivity {
    private String TAG = "TeacherLandingActivity";
    RecyclerView rvStudentApp;
    private ArrayList<StudentAppInfo> studentAppInfos;
    TextView tvCourseId, tvCourseName, tvClassRoom, tvStudentId, tvTeacherId, tvStart1, tvEnd1, tvStart2, tvEnd2;
    String id, week, name, stuId, classroom, techId, start1, start2, end1, end2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_landing);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        rvStudentApp = findViewById(R.id.rvStudentAppList);

        tvCourseId = findViewById(R.id.tvCourseId);
        tvCourseName = findViewById(R.id.tvCourseName);
        tvClassRoom = findViewById(R.id.tvClassRoom);
        tvStudentId = findViewById(R.id.tvStuId);
        tvTeacherId = findViewById(R.id.tvTeachId);
        tvStart1 = findViewById(R.id.tvStart1);
        tvStart2 = findViewById(R.id.tvStart2);
        tvEnd1 = findViewById(R.id.tvEnd1);
        tvEnd2 = findViewById(R.id.tvEnd2);

        getTeacherLoginData();
        getStudentInfo();
        startService(new Intent(getApplicationContext(), GetStudentInfoService.class));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getStudentInfo();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        startService(new Intent(getApplicationContext(), GetStudentInfoService.class));
        super.onDestroy();
    }

    private void getTeacherLoginData() {
        SharedPreferences teacherInfo = getSharedPreferences("teacherInfo", 0);
        id = teacherInfo.getString("tid", null);
        week = teacherInfo.getString("week", null);
        name = teacherInfo.getString("name", null);
        stuId = teacherInfo.getString("stuId", null);
        classroom = teacherInfo.getString("classroom", null);
        techId = teacherInfo.getString("techId", null);
        start1 = teacherInfo.getString("start1", null);
        start2 = teacherInfo.getString("start2", null);
        end1 = teacherInfo.getString("end1", null);
        end2 = teacherInfo.getString("end2", null);

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

    private void getStudentInfo() {
        Api.getInstance().getStudentInfo(techId, new ApiCallback() {
            @Override
            public void getJsonArray(String response) throws JSONException {
                if (response != null) {
                    JSONObject object = new JSONObject(response);
                    if (object.has("code") && object.getInt("code") == 200) {
                        Toast.makeText(getApplicationContext(), "获取学生信息成功", Toast.LENGTH_SHORT).show();
                        if (object.has("data")) {
                            JSONArray array = object.getJSONArray("data");
                            studentAppInfos = new ArrayList<>();
                            JSONObject studentObject;
                            for (int i = 0; i < array.length(); i++) {
                                studentObject = array.getJSONObject(i);
                                studentAppInfos.add(new StudentAppInfo(studentObject.getString("id"), studentObject.getString("appName"), studentObject.getString("createTime"), studentObject.getString("stuId"), studentObject.getString("techId"), studentObject.getString("week"), studentObject.getString("courseName")));
                            }

                            if (studentAppInfos.size() > 0) {
                                rvStudentApp.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                StudentAppAdapter studentAppAdapter = new StudentAppAdapter(getApplicationContext(), studentAppInfos);
                                rvStudentApp.setAdapter(studentAppAdapter);
                            }
                        }
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
}
