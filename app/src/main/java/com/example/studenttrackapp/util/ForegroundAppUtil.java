package com.example.studenttrackapp.util;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;
import java.util.Objects;

public class ForegroundAppUtil {
    private static final long END_TIME = System.currentTimeMillis();
    private static final long TIME_INTERVAL = 5 * 60000;
    private static final long START_TIME = END_TIME - TIME_INTERVAL;

    /**
     * 获取栈顶的应用包名
     */
    public static String getForegroundActivityName(Context context) {
        String currentClassName = "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager manager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            currentClassName = Objects.requireNonNull(manager.getRunningTasks(1).get(0).topActivity).getPackageName();
        } else {
            UsageStats initStat = getForegroundUsageStats(context, START_TIME, END_TIME);
            if (initStat != null) {
                currentClassName = initStat.getPackageName();
            }
        }
        Log.d("Test", "name is " + currentClassName);
        return currentClassName;
    }


    /**
     * 判断当前应用是否在前台
     */
    public static boolean isForegroundApp(Context context) {
        return TextUtils.equals(getForegroundActivityName(context), context.getPackageName());
    }

    /**
     * 获取时间段内，
     */
    public static long getTotleForegroundTime(Context context) {
        UsageStats usageStats = getCurrentUsageStats(context, START_TIME, END_TIME);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return usageStats != null ? usageStats.getTotalTimeInForeground() : 0;
        }
        return 0;
    }

    /**
     * 获取记录前台应用的UsageStats对象
     */
    private static UsageStats getForegroundUsageStats(Context context, long startTime, long endTime) {
        UsageStats usageStatsResult = null;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            List<UsageStats> usageStatses = getUsageStatsList(context, startTime, endTime);
            if (usageStatses == null || usageStatses.isEmpty()) return null;
            for (UsageStats usageStats : usageStatses) {
                if (usageStatsResult == null || usageStatsResult.getLastTimeUsed() < usageStats.getLastTimeUsed()) {
                    usageStatsResult = usageStats;
                }
            }
        }
        return usageStatsResult;
    }

    /**
     * 获取记录当前应用的UsageStats对象
     */
    public static UsageStats getCurrentUsageStats(Context context, long startTime, long endTime) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            List<UsageStats> usageStatses = getUsageStatsList(context, startTime, endTime);
            if (usageStatses == null || usageStatses.isEmpty()) return null;
            for (UsageStats usageStats : usageStatses) {
                if (TextUtils.equals(usageStats.getPackageName(), context.getPackageName())) {
                    return usageStats;
                }
            }
        }
        return null;
    }

    /**
     * 通过UsageStatsManager获取List<UsageStats>集合
     */
    public static List<UsageStats> getUsageStatsList(Context context, long startTime, long endTime) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            UsageStatsManager manager = (UsageStatsManager) context.getApplicationContext().getSystemService(Context.USAGE_STATS_SERVICE);
            List<UsageStats> usageStatses = manager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, startTime, endTime);
            if (usageStatses == null || usageStatses.size() == 0) {// 没有权限，获取不到数据
                Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
                return null;
            }
            return usageStatses;
        }
        return null;
    }
}
