package com.example.studenttrackapp.net;

import android.util.Log;

import org.json.JSONException;

public class Api {
    public static final String baseUrl = "http://2947v6l492.zicp.vip";
    private static Api mInstance;
    private static String TAG = "Api";

    private Api() {
        ApiManager.init();
    }

    public static void init() {
        if (mInstance == null) {
            mInstance = new Api();
        }
    }

    public static Api getInstance() {
        if (mInstance != null) {
            return mInstance;
        }

        throw new IllegalArgumentException("Should call Api.init() first!");
    }

    public void studentLogin(String studentNo, String pwd, String mac, ApiCallback callback) {
        String apiEndpoint = "student/login?stuNo=" + studentNo + "&pwd=" + pwd + "&mac=" + mac;
        String url = baseUrl + "/" + apiEndpoint;
        String json = "";

        Log.d(TAG, "student login url is " + apiEndpoint);
        ApiManager.getInstance().postWithAuthorisation(url, json, getRequestCallback(apiEndpoint, callback));
    }

    public void studentRegister(String account, String psw, String studentNo, ApiCallback callback) {
        String apiEndpoint = "student/save";
        String url = baseUrl + "/" + apiEndpoint;
        String json = "{"
                + "\"name\": \"" + account + "\","
                + "\"pwd\": \"" + psw + "\","
                + "\"stuNo\": " + studentNo
                + "}";

        Log.d(TAG, "student register request is " + json);
        ApiManager.getInstance().postWithAuthorisation(url, json, getRequestCallback(apiEndpoint, callback));
    }

    public void teacherRegister(String account, String pwd, String techNo, ApiCallback callback) {
        String apiEndpoint = "teacher/save";
        String url = baseUrl + "/" + apiEndpoint;
        String json = "{"
                + "\"name\": \"" + account + "\","
                + "\"pwd\": \"" + pwd + "\","
                + "\"techNo\": " + techNo
                + "}";

        ApiManager.getInstance().postWithAuthorisation(url, json, getRequestCallback(apiEndpoint, callback));
    }

    public void getStudentInfo(String techID, String courseId, ApiCallback callback) {
        String apiEndpoint = "record/get?techID=" + techID + "&courseId=" + courseId;
        String url = baseUrl + "/" + apiEndpoint;
        String json = "";

        ApiManager.getInstance().postWithAuthorisation(url, json, getRequestCallback(apiEndpoint, callback));
    }

    public void teacherLogin(String account, String psw, ApiCallback callback) {
        String apiEndpoint = "teacher/login?techNo=" + account + "&pwd=" + psw;
        String url = baseUrl + "/" + apiEndpoint;
        String json = "";
        ApiManager.getInstance().postWithAuthorisation(url, json, getRequestCallback(apiEndpoint, callback));
    }

    public void sendAppRecord(String appName, String courseName, String createTime, String stuId, String techId, ApiCallback callback) {
        String apiEndpoint = "record/save";
        String url = baseUrl + "/" + apiEndpoint;
        String json = "{"
                + "\"appName\": \"" + appName + "\","
                + "\"courseName\": \"" + courseName + "\","
                + "\"createTime\": \"" + createTime + "\","
                + "\"stuId\": \"" + stuId + "\","
                + "\"techId\": " + techId
                + "}";

        Log.d(TAG, "request to sendAppRecord is " + json);
        ApiManager.getInstance().postWithAuthorisation(url, json, getRequestCallback(apiEndpoint, callback));
    }

    private static RequestCallback getRequestCallback(String apiEndpoint, ApiCallback callback) {
        return new RequestCallback() {
            @Override
            public void onSuccess(String response) throws JSONException {
                Log.d(TAG, apiEndpoint + ": onSuccess - " + response);
                if (callback != null) {
                    callback.getJsonArray(response);
                }
            }

            @Override
            public void onFailure(Throwable throwable, int statuscode) {
                throwable.printStackTrace();
                if (callback != null) {
                    callback.onFailure(apiEndpoint, statuscode);
                }
            }

            @Override
            public void onTimeout() {
                if (callback != null) {
                    callback.onTimeout(apiEndpoint);
                }
            }
        };
    }

}
