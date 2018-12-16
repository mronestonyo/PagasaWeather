package com.mronestonyo.pagasaweather.model;


import com.google.gson.annotations.SerializedName;

public class Coordinate {

    @SerializedName("lon")
    private double longitude;
    @SerializedName("lat")
    private double langitude;


    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLangitude() {
        return langitude;
    }

    public void setLangitude(double langitude) {
        this.langitude = langitude;
    }
}
