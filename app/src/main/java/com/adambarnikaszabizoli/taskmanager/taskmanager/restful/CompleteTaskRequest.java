package com.adambarnikaszabizoli.taskmanager.taskmanager.restful;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class CompleteTaskRequest extends BaseObjectRequest {

    public CompleteTaskRequest(Response.Listener successListener, Response.ErrorListener errorListener, Map<String, String> requestBody) {
        super(Method.PUT, BaseObjectRequest.DOMAIN + "/api/task/status?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im56b2xpQGhhbGN5b25tb2JpbGUuY29tIiwiZXhwaXJlcyI6MTQyMTI0NzQ4M30.vetHh-1z3RLKa_Xhm2rcq7wnHOwjGEe9C89e_x3jgiI", requestBody, successListener, errorListener);
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            System.out.println("MESSAGE IS HEREEEE");
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
