package com.mronestonyo.pagasaweather.presenter;

import android.util.Log;

import com.mronestonyo.pagasaweather.model.DataManager;
import com.mronestonyo.pagasaweather.model.WeatherResponse;
import com.mronestonyo.pagasaweather.view.IView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class Presenter implements IBasePresenter {

  private DataManager mDataManager;
  private IView view;
  private Observable<WeatherResponse> responseObservable;

  public Presenter(DataManager dataManager, IView view) {
    this.mDataManager = dataManager;
    this.view = view;
  }

  @Override
  public void getWeatherForCity(String city) {

    showLoading();

    responseObservable = mDataManager.getWeatherData(city);
    responseObservable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            new DisposableObserver<WeatherResponse>() {

              @Override
              public void onNext(WeatherResponse weatherResponse) {

                view.showWeatherData(weatherResponse);
              }

              @Override
              public void onError(Throwable t) {
                Log.d("onError", t.toString());
                hideLoading();
                failedToGetDataForCity();
              }

              @Override
              public void onComplete() {
                Log.d("TAG", "onComplete");
                hideLoading();
              }
            }
        );
  }

  @Override
  public void unsubscribe() {
    responseObservable.unsubscribeOn(Schedulers.io());
  }

  @Override
  public void showLoading() {
    view.showLoadingDialog();
  }

  @Override
  public void hideLoading() {
    view.hideLoadingDialog();
  }

  @Override
  public void failedToGetDataForCity() {
    view.errorLoadingData();
  }
}
