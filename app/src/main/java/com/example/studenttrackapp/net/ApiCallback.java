package com.example.studenttrackapp.net;

import org.json.JSONException;

public interface ApiCallback {
    String TAG = "ApiCallback";

    void getJsonArray(String response) throws JSONException;

    void onTimeout(String apiEndpoint);

    void onFailure(String apiEndpoint, int statuscode);
}
