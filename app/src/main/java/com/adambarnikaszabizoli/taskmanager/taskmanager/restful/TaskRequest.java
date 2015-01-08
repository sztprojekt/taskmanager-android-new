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
import java.util.List;
import java.util.Map;

public class TaskRequest extends BaseObjectRequest{

    public static List<Task> tasks;
    private int taskID = 0;

    public TaskRequest(Response.Listener successListener, Response.ErrorListener errorListener, int id) {
        super(Method.GET, BaseObjectRequest.DOMAIN + "/api/" + ((id > 0) ? "task/" + id : "tasks") + "?&token=" + TaskManagerApplication.getToken()
                , null, successListener, errorListener);
        tasks = new ArrayList<Task>();
        taskID = id;
    }
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            System.out.println("RESPONSE HEREEEEEE");
            System.out.println(json);
            JSONObject jsonData = new JSONObject(json);
            JSONArray jsonTasks = jsonData.getJSONObject("data").getJSONArray("tasks");

            for(int i=0; i< jsonTasks.length(); i++) {
                JSONObject taskJson = jsonTasks.getJSONObject(i);
                Task task = new Task();
                task.setName(taskJson.getString("name"));
                task.setId(taskJson.getInt("id"));
                task.setStatus(taskJson.getBoolean("status"));
                tasks.add(task);
            }
            return Response.success(jsonTasks,null);
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
//String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
//System.out.println("RESPONSE HEREEEEEE");
//        System.out.println(json);
//
//        if (taskID < 1) {
//        JSONObject jsonData = new JSONObject(json);
//        JSONArray jsonTasks = jsonData.getJSONObject("data").getJSONArray("tasks");
//
//        for(int i=0; i< jsonTasks.length(); i++) {
//        JSONObject taskJson = jsonTasks.getJSONObject(i);
//        Task task = new Task();
//        task.setName(taskJson.getString("name"));
//        task.setId(taskJson.getInt("id"));
//        task.setStatus(taskJson.getBoolean("status"));
//        tasks.add(task);
//        return Response.success(jsonTasks,null);
//        }
//        } else {
//
//        }
//        return Response.success("YOYOYOYOYO",null);