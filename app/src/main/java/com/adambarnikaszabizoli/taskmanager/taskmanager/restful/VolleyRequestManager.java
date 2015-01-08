package com.adambarnikaszabizoli.taskmanager.taskmanager.restful;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyRequestManager {

    private static VolleyRequestManager sRequestManager;
    private static RequestQueue sRequestQueue;


    public VolleyRequestManager(Context context) {
        sRequestQueue = Volley.newRequestQueue(context);
    }

    public static VolleyRequestManager getSharedInstance(Context context) {
        if (sRequestManager == null) {
            sRequestManager = new VolleyRequestManager(context);
        }
        return sRequestManager;
    }

    public RequestQueue getRequestQueue() {
        return sRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

}
