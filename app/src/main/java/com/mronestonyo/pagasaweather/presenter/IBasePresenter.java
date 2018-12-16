package com.mronestonyo.pagasaweather.presenter;


public interface IBasePresenter {

  void getWeatherForCity(String city);
  void unsubscribe();
  void showLoading();
  void hideLoading();
  void failedToGetDataForCity();
}
