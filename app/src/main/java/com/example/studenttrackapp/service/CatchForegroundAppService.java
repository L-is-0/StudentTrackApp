package com.example.studenttrackapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.studenttrackapp.util.ForegroundAppUtil;

public class CatchForegroundAppService extends Service {
    private String TAG = "CatchForeGroundAppService";

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
            Toast.makeText(getApplicationContext(), foregroundActivityName, Toast.LENGTH_SHORT).show();
            handler.postDelayed(r, 10000);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.postDelayed(r, 5000);
        return START_STICKY;
    }
}
