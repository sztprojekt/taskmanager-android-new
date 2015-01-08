package com.adambarnikaszabizoli.taskmanager.taskmanager.restful;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import java.util.Map;

public abstract class BaseObjectRequest<T> extends Request<T> {
    public static final String DOMAIN = "http://sztprojekt-taskmanager.herokuapp.com";
    private Response.Listener<T> mSuccessListener;
    private Map<String, String> mRequestBody;

    //endregion    //region CONSTRUCTORS
    public BaseObjectRequest(int method, String url, Response.Listener<T> successListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mSuccessListener = successListener;
    }

    public BaseObjectRequest(int method, String url, Map<String, String> requestBody, Response.Listener<T> successListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mRequestBody = requestBody;
        this.mSuccessListener = successListener;
    }

    //endregion    //region INHERITED METHODS
    @Override
    protected void deliverResponse(T response) {
        if (mSuccessListener != null && !isCanceled()) {
            mSuccessListener.onResponse(response);
        }
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mRequestBody;
    }

    @Override
    protected abstract Response<T> parseNetworkResponse(NetworkResponse response);
    //endregion
}
