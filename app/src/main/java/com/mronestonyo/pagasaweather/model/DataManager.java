package com.mronestonyo.pagasaweather.model;

import io.reactivex.Observable;


public class DataManager {

    private final String API_KEY = "71596826ab19970f334832add051d70d";

    public Observable<WeatherResponse> getWeatherData(String cityName)
    {
      WeatherAPI weatherAPI = new WeatherAPI();
      return  weatherAPI.getService().getWeatherCall(cityName, API_KEY);
    }

}
