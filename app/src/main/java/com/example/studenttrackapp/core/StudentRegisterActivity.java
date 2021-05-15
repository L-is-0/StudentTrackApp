package com.example.studenttrackapp.core;

import android.content.Intent;
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

public class StudentRegisterActivity extends AppCompatActivity {
    EditText etStudentName, etStudentPsw, etStudentNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        etStudentName = findViewById(R.id.etStuName);
        etStudentPsw = findViewById(R.id.etStudentPsw);
        etStudentNo = findViewById(R.id.etStudentNo);
    }

    public void onStudentLoginClicked(View view) {
        Intent mIntent = new Intent(this, StudentLoginActivity.class);
        startActivity(mIntent);
        finish();
    }

    public void onStudentRegisterClicked(View view) {
        String name = etStudentName.getText().toString();
        String psw = etStudentPsw.getText().toString();
        String stuNo = etStudentNo.getText().toString();
        Api.getInstance().studentRegister(name, psw, stuNo, new ApiCallback() {
            @Override
            public void getJsonArray(String response) throws JSONException {
                if (response != null) {
                    JSONObject object = new JSONObject(response);
                    if (object.has("code") && object.getInt("code") == 200) {
                        Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                        studentRegister();
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

    private void studentRegister() {
        Intent mIntent = new Intent(this, StudentLoginActivity.class);
        startActivity(mIntent);
        finish();
    }
}
