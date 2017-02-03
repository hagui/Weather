package com.a2305.weather.core.api;


import android.content.Context;
import android.net.ConnectivityManager;

public abstract class ApiUtility {

    protected final String mServer = "http://openweathermap.org/";
    protected final String mKey = "8194ea842a9aef80a798c8ac0c320ec4";
    protected final ApiHeader  mHeader = new ApiHeader();
    protected Context mContext;

    public ApiUtility(Context mContext) {
        this.mContext = mContext;
    }

    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        return false;
    }
}
