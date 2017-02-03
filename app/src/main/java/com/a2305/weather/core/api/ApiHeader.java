package com.a2305.weather.core.api;


import org.json.JSONException;
import org.json.JSONObject;

public class ApiHeader {

    public boolean success;
    public String message;
    public int code;
    public String version;

    public ApiHeader() {
        clear();
    }

    public void parse(JSONObject object) throws JSONException {
        clear();
        success = object.getBoolean("success");
        code = object.getInt("code");
        version = object.getString("version");
        message = object.getString("message");
    }

    public void clear() {
        success = false;
        message = "";
        code = -1;
        version = "";
    }



}
