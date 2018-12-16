package com.mronestonyo.pagasaweather.view;

import com.mronestonyo.pagasaweather.model.WeatherResponse;


public interface IView {
  void showWeatherData(WeatherResponse response);
  void showLoadingDialog();
  void hideLoadingDialog();
  void errorLoadingData();
}
