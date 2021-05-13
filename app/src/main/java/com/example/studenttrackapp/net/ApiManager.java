package com.example.studenttrackapp.net;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiManager {
    public static final MediaType TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = "ApiManager";
    private static final long API_TIMEOUT_SECONDS = 10;

    private final OkHttpClient okHttpClient;
    // use handler to return the response to the thread that made the request
    private static final Handler handler = new Handler(Looper.getMainLooper());

    private static ApiManager mInstance;

    // class constructor
    private ApiManager() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(API_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(API_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(API_TIMEOUT_SECONDS, TimeUnit.SECONDS)
//                .authenticator(new AccessTokenAuthenticator(accessTokenRepository))
                .build();
    }

    public ApiManager(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public static void init() {
        if (mInstance == null) {
            mInstance = new ApiManager();
        }
    }

    public static ApiManager getInstance() {
        if (mInstance != null) {
            return mInstance;
        }
        throw new IllegalArgumentException("Should call ApiManager.init() first!");
    }

    public static void post(String url, String jsonStr, RequestCallback requestCallback) {
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(jsonStr, TYPE_JSON))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(API_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(API_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(API_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String response_str = Objects.requireNonNull(response.body()).string();
                    handler.post(() -> {
                        try {
                            requestCallback.onSuccess(response_str);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, e.getMessage());
                            if (!response_str.equals(""))
                                Log.e(TAG, response_str);
                        }
                    });
                } else {
                    handler.post(() -> requestCallback.onFailure(new IOException(response.message() + response.toString() + ", url = " + call.request().url().toString()), response.code()));
                }
            }
        });
    }

    public static CustomApiResponse postSync(String url, String jsonStr) {
        CustomApiResponse response = new CustomApiResponse(url);
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(jsonStr, TYPE_JSON))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(API_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(API_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(API_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build();
        try {
            response.setResponse(okHttpClient.newCall(request).execute());
        }
        catch (IOException e) {
            Log.d(TAG, e.getMessage());
            response.setException(e);
        }
        return response;
    }

    public void getWithAuthorisation(String url, RequestCallback reqCallback) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        addCallBack(reqCallback, request);
    }

    public CustomApiResponse getWithAuthorisationSync(String url) {
        CustomApiResponse response = new CustomApiResponse(url);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            response.setResponse(okHttpClient.newCall(request).execute());
        }
        catch (IOException e) {
            Log.d(TAG, e.getMessage());
            response.setException(e);
        }
        return response;
    }

    public void postWithAuthorisation(String url, String jsonStr, RequestCallback reqCallback) {
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(jsonStr, TYPE_JSON))
                .build();
        addCallBack(reqCallback, request);
    }

    public CustomApiResponse postWithAuthorisationSync(String url, String jsonStr) {
        CustomApiResponse response = new CustomApiResponse(url);
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(jsonStr, TYPE_JSON))
                .build();
        try {
            response.setResponse(okHttpClient.newCall(request).execute());
        }
        catch (IOException e) {
            Log.d(TAG, e.getMessage());
            response.setException(e);
        }
        return response;
    }

    private void addCallBack(final RequestCallback requestCallback, Request request) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String response_str = Objects.requireNonNull(response.body()).string();
                    handler.post(() -> {
                        try {
                            requestCallback.onSuccess(response_str);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, e.getMessage());
                            if (!response_str.equals(""))
                                Log.e(TAG, response_str);
                        }
                    });
                } else {
                    handler.post(() -> requestCallback.onFailure(new IOException(response.message() + response.toString() + ", url = " + call.request().url().toString()), response.code()));
                }
            }
        });
    }
}
