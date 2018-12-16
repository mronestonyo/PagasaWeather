package com.mronestonyo.pagasaweather.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherResponse {

  @SerializedName("coord")
  private Coordinate coordinate;
  @SerializedName("weather")
  private ArrayList weatherList;
  @SerializedName("main")
  private MainWeatherData mWeatherData;


  public Coordinate getCoordinate() {
    return coordinate;
  }

  public void setCoordinate(Coordinate coordinate) {
    this.coordinate = coordinate;
  }

  public ArrayList getWeather() {
    return weatherList;
  }

  public void setWeather(ArrayList weather) {
    this.weatherList = weather;
  }

  public MainWeatherData getWeatherData() {
    return mWeatherData;
  }

  public void setWeatherData(MainWeatherData mWeatherData) {
    this.mWeatherData = mWeatherData;
  }
}
