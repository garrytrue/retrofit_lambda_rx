package com.garrytrue.retrofitlambdaxjava.mvp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class CurrentWeatherModel extends BaseWeatherModel implements Serializable {
    @SerializedName("name")
    private String locationName;
    @SerializedName("dt")
    private long timestamp;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private ArrayList<Weather> weather;
    private Main main;

    @SuppressWarnings("unused")
    public class Main {
        private float temp;
        public float temp_min;
        public float temp_max;

        public float getTemp() {
            return temp;
        }
    }

    public Weather getWeather() {
        return weather.get(0);
    }

    public Main getMain() {
        return main;
    }

    public String getLocationName() {
        return locationName;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Location ").append(locationName).append(" TimeStamp ").append(timestamp).toString();
    }
}
