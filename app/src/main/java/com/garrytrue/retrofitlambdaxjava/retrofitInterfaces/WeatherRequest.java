package com.garrytrue.retrofitlambdaxjava.retrofitInterfaces;


import com.garrytrue.retrofitlambdaxjava.mvp.models.CurrentWeatherModel;
import com.garrytrue.retrofitlambdaxjava.mvp.models.WeatherForecastModel;

import java.util.Map;

import retrofit.http.GET;
import retrofit.http.QueryMap;
import rx.Observable;

public interface WeatherRequest {

    String Q_KEY = "q";
    String UNITS_KEY = "units";
    String UNITS_VALUE = "metric";
    String APP_ID_KEY = "appid";
    String APP_ID_VALUE = "57cca4e37a0cb87859364ffca5cdc1fc";

    @GET("/data/2.5/weather")
    Observable<CurrentWeatherModel> fetchCurrentWeather(@QueryMap Map<String, String> requestMap);

    @GET("/data/2.5/forecast/daily")
    Observable<WeatherForecastModel> fetchWeatherForecasts(@QueryMap Map<String, String> requestMap);
}
