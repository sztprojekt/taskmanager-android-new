package com.adambarnikaszabizoli.taskmanager.taskmanager.restful;

//4/-PK806oUqz573TU8VHKVjTUGGyz1YNQSPzHaAcI-sIE.8usCcjtWf3sZrjMoGjtSfTp4MfiAlQI

import com.adambarnikaszabizoli.taskmanager.taskmanager.TaskManagerApplication;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class GoogleAuthRequest extends BaseObjectRequest{

    public GoogleAuthRequest(Response.Listener successListener, Response.ErrorListener errorListener, Map<String, String> requestBody) {
        super(Method.POST, BaseObjectRequest.DOMAIN + "/api/google/auth", requestBody, successListener, errorListener);
    }

    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Token token = new Token(json);
            token.parseJsonResponse();
            TaskManagerApplication.setToken(token);
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
