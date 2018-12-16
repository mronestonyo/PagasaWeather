package com.mronestonyo.pagasaweather.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.internal.LinkedTreeMap;
import com.mronestonyo.pagasaweather.R;
import com.mronestonyo.pagasaweather.base.BaseFragment;
import com.mronestonyo.pagasaweather.model.DataManager;
import com.mronestonyo.pagasaweather.model.WeatherResponse;
import com.mronestonyo.pagasaweather.presenter.Presenter;
import com.mronestonyo.pagasaweather.view.IView;

public class WeatherInformationMainScreenFragment extends BaseFragment implements IView {

    private ProgressDialog mProgressDialog;
    private Presenter presenter;
    private TextView mCurrentTemp;
    private TextView mMaxTemp;
    private TextView mMinTemp;
    private ImageView weatherIcon;
    private TextView mPressure;
    private TextView mHumidity;
    private TextView mCityName;
    private LinearLayout mWeatherLayout;
    private TextView mDetail;
    private String city;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_information_main_screen, container, false);

        setHasOptionsMenu(true);

        if (getArguments() != null) {
            city = getArguments().getString("LOCATION_NAME");
            getActivity().setTitle("");
            init(view);
            initData();
        }

        return view;
    }

    private void init(View view) {
        mCurrentTemp = view.findViewById(R.id.current_temp);
        mMaxTemp = view.findViewById(R.id.max_temp);
        mMinTemp = view.findViewById(R.id.min_temp);
        weatherIcon = view.findViewById(R.id.icon);
        mPressure = view.findViewById(R.id.pressure);
        mHumidity = view.findViewById(R.id.humidity);
        mCityName = view.findViewById(R.id.city);
        mWeatherLayout = view.findViewById(R.id.weather);
        mDetail = view.findViewById(R.id.detail);
    }

    private void initData() {
        mCityName.setText(city);
        DataManager dataManager = new DataManager();
        presenter = new Presenter(dataManager, this);
        presenter.getWeatherForCity(city);
    }

    @Override
    public void showWeatherData(WeatherResponse response) {
        mCurrentTemp.setText(String.valueOf(response.getWeatherData().getTemp()) + "℃");
        mMaxTemp.setText(String.valueOf(response.getWeatherData().getTemp_max()) + "℃");
        mMinTemp.setText(String.valueOf(response.getWeatherData().getTemp_min()) + "℃");
        mPressure.setText(String.valueOf(response.getWeatherData().getPressure()) + " hPa");
        mHumidity.setText(String.valueOf(response.getWeatherData().getHumidity()) + " %");

        Object weatherDetail = response.getWeather().get(0);
        LinkedTreeMap linkedTreeMap = (LinkedTreeMap) weatherDetail;
        String iconURL = String.valueOf(linkedTreeMap.get("icon"));

        mDetail.setText(String.valueOf(linkedTreeMap.get("main")));
        Glide.with(getViewContext())
                .load("http://openweathermap.org/img/w/" + iconURL + ".png")
                .apply(RequestOptions.circleCropTransform())
                .into(weatherIcon);
    }

    @Override
    public void showLoadingDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getViewContext());
            mProgressDialog.setTitle("Pagasa Weather");
            mProgressDialog.setMessage("Loading...");
        }
        mProgressDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        mProgressDialog.hide();
        mWeatherLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void errorLoadingData() {
        mWeatherLayout.setVisibility(View.INVISIBLE);
        Toast.makeText(getViewContext(),
                "Something went wrong with the server.",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh: {
                presenter.getWeatherForCity(city);
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    public void onDestroy() {
        presenter.unsubscribe();
        super.onDestroy();
    }
}
