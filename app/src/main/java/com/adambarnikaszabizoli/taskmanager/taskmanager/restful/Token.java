package com.adambarnikaszabizoli.taskmanager.taskmanager.restful;

import org.json.JSONObject;

public class Token
{
    private String json;
    private String token;

    public Token(String json) {
        this.json = json;
    }

    public void parseJsonResponse() throws Exception{
        JSONObject jsonObject = new JSONObject(json);
        token = jsonObject.getJSONObject("data").getString("token");
    }

    public String toString() {
        return token;
    }
}
