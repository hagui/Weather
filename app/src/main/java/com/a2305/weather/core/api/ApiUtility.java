package com.a2305.weather.core.api;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.goebl.david.Response;
import com.goebl.david.Webb;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public abstract class ApiUtility {

    protected final String mServer = "http://openweathermap.org/";
    protected final String mKey = "8194ea842a9aef80a798c8ac0c320ec4";
    protected final ApiHeader mHeader = new ApiHeader();
    protected Context mContext;

    public ApiUtility(Context mContext) {
        this.mContext = mContext;
    }


    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }


    private boolean preprocessResponse(JSONObject object, ApiCallback callback) {
        if (object != null) {

            try {
                mHeader.parse(object);
                clearData();
                if (!mHeader.success) {
                    if (callback != null) {
                        callback.onApiError(mHeader.message);
                    }
                    return false;
                }
                processResponse(object);
            } catch (JSONException e) {
                mHeader.success = false;
                mHeader.message = e.getMessage();
                if (callback != null) {
                    callback.onApiError(mHeader.message);
                }
                return false;
            }
        } else {
            mHeader.success = false;
            mHeader.message = "Pas de réponse";
            if (callback != null) {
                callback.onApiError(mHeader.message);
            }
            return false;
        }
        if (callback != null) {
            callback.onApiEnd();
        }
        return true;
    }




    protected boolean queryGet(Webb webb,String url, JSONObject params,ApiCallback callback, String description) {
        if (callback != null) {
            callback.onApiStart(description);
        }
        mHeader.clear();
        clearData();
        if (isConnected()) {
            try {
                JSONObject json = (JSONObject) webbGet(webb,url,params,callback,description);
                return preprocessResponse(json, callback);
            } catch (Exception e) {
                if (callback != null) {
                    callback.onApiError(e.toString());
                }
            }
        } else {
            if (callback != null) {
                callback.onApiError("Pas de réseau");
            }
        }
        return false;
    }

    protected boolean queryPost(Webb webb , String url, JSONObject params, ApiCallback callback, String description) {
        if (callback != null) {
            callback.onApiStart(description);
        }
        mHeader.clear();
        clearData();
        if (isConnected()) {
            try {
                JSONObject json = (JSONObject) webbPost(webb,url, params , callback , description);
                return preprocessResponse(json, callback);
            } catch (Exception e) {
                if (callback != null) {
                    callback.onApiError(e.toString());
                }
            }
        } else {
            if (callback != null) {
                callback.onApiError("Pas de réseau");
            }
        }
        return false;
    }


    @Nullable
    protected Object webbPost(Webb webb, @NonNull String path, @Nullable JSONObject body, @Nullable ApiCallback callback, @NonNull String description) throws ApiSymfonyException {
        try {
            if (preRequest(callback, description)) {
                //webb.setDefaultHeader("api-client-version", 1);
                Response<JSONObject> response = webb.post(path)
                        .body(body)
                        .ensureSuccess()
                        .asJsonObject();
                if(response != null){
                    callback.onApiEnd();
                }
                return response.getBody();
            }
        } catch (Exception e) {
            handleException(callback, e);
        }
        return null;
    }



    @Nullable
    protected Object webbGet(Webb webb, @NonNull String path, @Nullable JSONObject body, @Nullable ApiCallback callback, @NonNull String description) throws ApiSymfonyException {
        try {
            if (preRequest(callback, description)) {
               // webb.setDefaultHeader("api-client-version", 1);
                Response<JSONObject> response = webb.post(path)
                        .body(body)
                        .ensureSuccess()
                        .asJsonObject();
                if(response != null){
                    callback.onApiEnd();
                }
                return response.getBody();
            }
        } catch (Exception e) {
            handleException(callback, e);
        }
        return null;
    }



    protected boolean preRequest(ApiCallback callback, String description) throws Exception {
        if (callback != null) {
            callback.onApiStart(description);
        }
        if (isConnected()) {
            return true;
        }
        if (callback != null) {
            callback.onApiError("Pas de réseau");
        }
        return false;
    }

    protected void handleException(ApiCallback callback, Exception e) {
        if (callback != null) {
            if (e instanceof UnknownHostException) {
                callback.onApiError("Problème réseau, essayer d'activer un reseau Wifi/4G ou de passer de l'un à l'autre");
            } else if (e instanceof ConnectException) {
                callback.onApiError("Pas de réseau");
            } else {
                callback.onApiError(e.toString());
            }
        }
    }


    protected abstract void processResponse(JSONObject object) throws JSONException;

    protected abstract void clearData();


}
