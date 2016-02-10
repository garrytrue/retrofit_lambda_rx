package com.garrytrue.retrofitlambdaxjava.events;


import com.garrytrue.retrofitlambdaxjava.mvp.models.WeatherForecastModel;

public class EventReceiveWeatherForecast {
    private final WeatherForecastModel model;
    public EventReceiveWeatherForecast(WeatherForecastModel model){
        this.model = model;
    }

    public WeatherForecastModel getModel() {
        return model;
    }
}
