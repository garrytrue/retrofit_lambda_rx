package com.garrytrue.retrofitlambdaxjava.mvp.views;


import com.garrytrue.retrofitlambdaxjava.mvp.models.CurrentWeatherModel;
import com.garrytrue.retrofitlambdaxjava.mvp.models.WeatherForecastModel;

public interface IWeatherFragmentView {

    void updateForecastView(WeatherForecastModel forecastModel);

    void showProgress();

    void hideProgress();

    void updateHeader(CurrentWeatherModel model);

    void showError();
}
