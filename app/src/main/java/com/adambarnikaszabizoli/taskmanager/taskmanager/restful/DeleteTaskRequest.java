package com.adambarnikaszabizoli.taskmanager.taskmanager.restful;


import com.adambarnikaszabizoli.taskmanager.taskmanager.TaskManagerApplication;
import com.adambarnikaszabizoli.taskmanager.taskmanager.tasks.Task;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class DeleteTaskRequest extends BaseObjectRequest {
    public DeleteTaskRequest(Response.Listener successListener, Response.ErrorListener errorListener, int id) {
        super(Method.DELETE, BaseObjectRequest.DOMAIN + "/api/task/" + id + "?token=" + TaskManagerApplication.getToken(), null, successListener, errorListener);
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            System.out.println("RESPONSE HEREEEEEE");
            System.out.println(json);

            return Response.success(json,null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch(Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public int compareTo(Object another) {
        return 0;
    }
}
