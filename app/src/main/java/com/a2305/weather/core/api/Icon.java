package com.a2305.weather.core.api;


public enum Icon {

    CLEAR_SKY("clear sky", 800 ,"01"),
    THUNDERSTORM("Thunderstorm", 200 ,"11"),
    DRIZZLE("Drizzle", 300,"09"),
    RAIN("Rain" , 500 , "10"),
    SNOW("Snow", 600,"13"),
    ATMOSPHERE("Atmosphere", 700 , "50"),
    CLOUD("Clouds", 800 , "02"),
    //90X
    EXTREME("Extreme" , 900," " ),
    //9XX
    ADDITIONAL("Additional",900 ," ");




    private String name;
    private int codeIcon;
    private String iconName;


    private Icon(String toString ,int code , String icon){
        name = toString;
        codeIcon = code;
        iconName = icon;

    }

    @Override
    public String toString() {
        return name;
    }
}
