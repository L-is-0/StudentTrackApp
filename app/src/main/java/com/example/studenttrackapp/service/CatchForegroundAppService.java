package com.example.studenttrackapp.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.studenttrackapp.net.Api;
import com.example.studenttrackapp.net.ApiCallback;
import com.example.studenttrackapp.util.ForegroundAppUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class CatchForegroundAppService extends Service {
    private String TAG = "CatchForeGroundAppService";
    private String packname = "";
    private boolean isWithinAllowedTime = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Handler handler = new Handler();
    private Runnable r = new Runnable() {
        @Override
        public void run() {
            String foregroundActivityName = ForegroundAppUtil.getForegroundActivityName(getApplicationContext());
            Log.d(TAG, "app name is " + foregroundActivityName);
            if (!foregroundActivityName.equals("com.example.studenttrackapp") && isWithinAllowedTime) {
                sendAppRecord(foregroundActivityName);
            }
            handler.postDelayed(r, 2 * 60000);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences studentInfo = getSharedPreferences("studentInfo", 0);
        String start1 = studentInfo.getString("start1", null);
        String start2 = studentInfo.getString("start2", null);
        String end1 = studentInfo.getString("end1", null);
        String end2 = studentInfo.getString("end2", null);
        isWithinAllowedTime = isWithinTime(start1, start2, end1, end2);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.postDelayed(r, 5000);
        return START_STICKY;
    }

    private void sendAppRecord(String appName) {
        SharedPreferences studentInfo = getSharedPreferences("studentInfo", 0);
        String name = studentInfo.getString("name", null);
        String stuId = studentInfo.getString("stuId", null);
        String techId = studentInfo.getString("techId", null);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String currentTime = dateFormat.format(System.currentTimeMillis());
        Api.getInstance().sendAppRecord(appName, name, currentTime, stuId, techId, new ApiCallback() {
            @Override
            public void getJsonArray(String response) throws JSONException {
                if (response != null) {
                    JSONObject object = new JSONObject(response);
                    if (object.getInt("code") == 200) {
                        Toast.makeText(getApplicationContext(), "成功发送app信息到接口", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "发送失败", Toast.LENGTH_SHORT).show();
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

    private boolean isWithinTime(String start1, String start2, String end1, String end2) {
        String[] startTimeSplit1 = start1.split(":");
        Calendar startTime1 = Calendar.getInstance();
        startTime1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTimeSplit1[0]));
        startTime1.set(Calendar.MINUTE, Integer.parseInt(startTimeSplit1[1]));
        startTime1.set(Calendar.MILLISECOND, 0);
        Log.d(TAG, "now is " + startTime1.get(Calendar.HOUR_OF_DAY) + ":" + startTime1.get(Calendar.MINUTE));


        String[] endTimeSplit1 = end1.split(":");
        Calendar endTime1 = Calendar.getInstance();
        endTime1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTimeSplit1[0]));
        endTime1.set(Calendar.MINUTE, Integer.parseInt(endTimeSplit1[1]));
        endTime1.set(Calendar.MILLISECOND, 0);
        Log.d(TAG, "now is " + endTime1.get(Calendar.HOUR_OF_DAY) + ":" + endTime1.get(Calendar.MINUTE));

        String[] startTimeSplit2 = start2.split(":");
        Calendar startTime2 = Calendar.getInstance();
        startTime2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTimeSplit2[0]));
        startTime2.set(Calendar.MINUTE, Integer.parseInt(startTimeSplit2[1]));
        startTime2.set(Calendar.MILLISECOND, 0);
        Log.d(TAG, "now is " + startTime2.get(Calendar.HOUR_OF_DAY) + ":" + startTime2.get(Calendar.MINUTE));

        String[] endTimeSplit2 = end2.split(":");
        Calendar endTime2 = Calendar.getInstance();
        endTime2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTimeSplit2[0]));
        endTime2.set(Calendar.MINUTE, Integer.parseInt(endTimeSplit2[1]));
        endTime2.set(Calendar.MILLISECOND, 0);
        Log.d(TAG, "now is " + endTime2.get(Calendar.HOUR_OF_DAY) + ":" + endTime2.get(Calendar.MINUTE));

        Calendar now = Calendar.getInstance();
        now.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Log.d(TAG, "now is " + now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE));

        if (now.getTimeInMillis() < startTime1.getTimeInMillis() || (endTime1.getTimeInMillis() < now.getTimeInMillis() && now.getTimeInMillis() < startTime2.getTimeInMillis()) || now.getTimeInMillis() > endTime2.getTimeInMillis()) {
            return true;
        }
        return false;
    }
}
