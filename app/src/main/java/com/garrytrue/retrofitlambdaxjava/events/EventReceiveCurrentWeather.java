package com.garrytrue.retrofitlambdaxjava.events;

import com.garrytrue.retrofitlambdaxjava.mvp.models.CurrentWeatherModel;


public class EventReceiveCurrentWeather {
        private CurrentWeatherModel model;

    public EventReceiveCurrentWeather(CurrentWeatherModel model) {
        this.model = model;
    }

    public CurrentWeatherModel getModel() {
        return model;
    }
}
