package com.example.studenttrackapp.net;

import org.json.JSONException;

public interface RequestCallback {
    void onSuccess(String response) throws JSONException;

    void onFailure(Throwable throwable, int statuscode);

    void onTimeout();
}
