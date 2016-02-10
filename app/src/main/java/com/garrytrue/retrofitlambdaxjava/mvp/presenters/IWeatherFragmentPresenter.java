package com.garrytrue.retrofitlambdaxjava.mvp.presenters;

import android.os.Bundle;

import com.garrytrue.retrofitlambdaxjava.mvp.models.CurrentWeatherModel;
import com.garrytrue.retrofitlambdaxjava.mvp.models.WeatherForecastModel;
import com.garrytrue.retrofitlambdaxjava.mvp.views.IWeatherFragmentView;


interface IWeatherFragmentPresenter extends IBasePresenter<IWeatherFragmentView> {
    void updateForecast(WeatherForecastModel forecastModel);

    void showProgress();

    void hideProgress();

    void startFetchData(String location);

    void updateHeader(CurrentWeatherModel model);

    void onStop();

    void showError();

    boolean isTasksBusy();

    void saveState(Bundle outState);

    void restoreState(Bundle savedState);

    void onResume();

}
