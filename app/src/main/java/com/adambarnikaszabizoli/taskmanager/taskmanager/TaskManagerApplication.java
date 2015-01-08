package com.adambarnikaszabizoli.taskmanager.taskmanager;

import android.app.Application;

import com.adambarnikaszabizoli.taskmanager.taskmanager.restful.Token;

public class TaskManagerApplication extends Application{
    private static Token token;
    public static void setToken(Token tokenParam) {
        token = tokenParam;
    }

    public static Token getToken() {
        return token;
    }
}
