package com.example.studenttrackapp.net;

import okhttp3.Response;

public class CustomApiResponse {
    private final String requestUrl;
    private Response response;
    private Exception exception;

    public CustomApiResponse(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }
}
