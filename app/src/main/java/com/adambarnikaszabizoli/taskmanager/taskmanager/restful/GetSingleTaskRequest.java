package com.adambarnikaszabizoli.taskmanager.taskmanager.restful;


import com.adambarnikaszabizoli.taskmanager.taskmanager.TaskManagerApplication;
import com.adambarnikaszabizoli.taskmanager.taskmanager.tasks.Task;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class GetSingleTaskRequest extends BaseObjectRequest{

    private int taskID = 0;
    public static Task responseTask;

    public GetSingleTaskRequest(Response.Listener successListener, Response.ErrorListener errorListener, int id) {
        super(Method.GET, BaseObjectRequest.DOMAIN + "/api/task/" + id + "?&token=" + TaskManagerApplication.getToken()
                , null, successListener, errorListener);
        taskID = id;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            System.out.println("RESPONSE HEREEEEEE");
            System.out.println(json);

            JSONObject jsonData = new JSONObject(json);
            JSONObject jsonTask = jsonData.getJSONObject("data").getJSONObject("task");

            responseTask = new Task();
            responseTask.setName(jsonTask.getString("name"));
            responseTask.setId(jsonTask.getInt("id"));
            responseTask.setStatus(jsonTask.getBoolean("status"));
            responseTask.setDueDateString(jsonTask.getString("due_date"));

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
