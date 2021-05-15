package com.example.studenttrackapp.event;

import android.os.Bundle;

public class AppEvent {
    public final String type;
    public String message = "";
    private Bundle extras;

    public AppEvent(String type) {
        this.type = type;
    }

    public AppEvent(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public AppEvent(String type, String message, Bundle extras) {
        this.type = type;
        this.message = message;
        this.extras = extras;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
