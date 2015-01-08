package com.adambarnikaszabizoli.taskmanager.taskmanager.restful;

import com.adambarnikaszabizoli.taskmanager.taskmanager.TaskManagerApplication;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class UpdateTaskRequest extends BaseObjectRequest{
    public UpdateTaskRequest(Response.Listener successListener, Response.ErrorListener errorListener, Map<String, String> requestBody) {
        super(Method.PUT, BaseObjectRequest.DOMAIN + "/api/task", requestBody, successListener, errorListener);
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            System.out.println(json);
            return Response.success(response,null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (Exception e) {
            System.out.println("ERROR PARSING TOKEN");
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public int compareTo(Object another) {
        return 0;
    }
}
