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

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class StudentLoginActivity extends AppCompatActivity {
    EditText etStudentNo, etStudentPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        etStudentNo = findViewById(R.id.etStudentNo);
        etStudentPsw = findViewById(R.id.etStudentPws);
    }

    public void onStudentLoginClicked(View view) {

        String stuNo = etStudentNo.getText().toString();
        String psw = etStudentPsw.getText().toString();
        String mac = getMacAddress();
        Api.getInstance().studentLogin(stuNo, psw, mac, new ApiCallback() {
            @Override
            public void getJsonArray(String response) throws JSONException {
                if (response != null) {
                    JSONObject object = new JSONObject(response);
                    if (object.has("code") && object.getInt("code") == 200) {
                        Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                        if (object.has("data")) {
                            JSONObject studentObject = object.getJSONObject("data");
                            if (studentObject.has("data") && !studentObject.isNull("data")) {
                                JSONObject mObject = studentObject.getJSONObject("data");
                                String id = mObject.getString("id");
                                String week = mObject.getString("week");
                                String name = mObject.getString("name");
                                String stuId = mObject.getString("stuId");
                                String classroom = mObject.getString("classroom");
                                String techId = mObject.getString("techId");
                                String start1 = mObject.getString("start1");
                                String start2 = mObject.getString("start2");
                                String end1 = mObject.getString("end1");
                                String end2 = mObject.getString("end2");
                                storeStudentInfo(id, week, name, stuId, classroom, techId, start1, start2, end1, end2);
                                studentLogin();
                            } else {
                                Toast.makeText(getApplicationContext(), "暂无绑定信息", Toast.LENGTH_SHORT).show();
                            }
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

    private void storeStudentInfo(String id, String week, String name, String stuId, String classroom, String techId, String start1, String start2, String end1, String end2) {
        SharedPreferences teacherInfo = getSharedPreferences("studentInfo", 0);
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

    private void studentLogin() {
        Intent mIntent = new Intent(this, StudentLandingActivity.class);
        startActivity(mIntent);
        finish();
    }

    public void onStudentRegisterClicked(View view) {
        Intent mIntent = new Intent(this, StudentRegisterActivity.class);
        startActivity(mIntent);
        finish();
    }

    public static String getMacAddress(){
        String macAddress ="";
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return macAddress;
    }
}
