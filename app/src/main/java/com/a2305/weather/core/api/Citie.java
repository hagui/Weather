package com.a2305.weather.core.api;



public class Citie {

    private String mName;
    private String mCountry;
    private long mId;
    private double mLon;
    private double mLat;


    public Citie(String name){
        this.mName = name;

    }

    public Citie(String mName, String mCountry, long mId, double mLon, double mLat) {
        this.mName = mName;
        this.mCountry = mCountry;
        this.mId = mId;
        this.mLon = mLon;
        this.mLat = mLat;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public double getmLon() {
        return mLon;
    }

    public void setmLon(double mLon) {
        this.mLon = mLon;
    }

    public double getmLat() {
        return mLat;
    }

    public void setmLat(double mLat) {
        this.mLat = mLat;
    }


    private class Weather{



    }
}
