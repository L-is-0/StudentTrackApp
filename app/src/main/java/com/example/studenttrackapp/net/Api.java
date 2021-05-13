package com.example.studenttrackapp.net;

public class Api {
    public static final String baseUrl = "baseUrl";
    private static Api mInstance;
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
}
