package com.mronestonyo.pagasaweather.model;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface APICall {


    @GET("weather?&units=metric")
    Observable<WeatherResponse> getWeatherCall(@Query("q") String city, @Query("APPID") String api);
}
