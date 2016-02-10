package com.garrytrue.retrofitlambdaxjava.mvp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;


public class WeatherForecastModel extends BaseWeatherModel implements Serializable {
    private final ArrayList<ForecastData> list = new ArrayList<>();

    @SuppressWarnings("unused")
    public class ForecastData {
        @SerializedName("dt")
        long timeStamp;
        private Temperature temp;
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        private final ArrayList<Weather> weather = new ArrayList<>();

        public Temperature getTemp() {
            return temp;
        }

        public Weather getWeather() {
            return weather.get(0);
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        @Override
        public String toString() {
            return new StringBuilder("[TMax: ")
                    .append(getTemp().getMax())
                    .append(", TMin: ")
                    .append(getTemp().getMin())
                    .append(", TimeStamp: ").append(timeStamp)
                    .append("]")
                    .append(" WeatherSize ")
                    .append(weather.size())
                    .append("]")
                    .toString();
        }
    }

    @SuppressWarnings("unused")
    public class Temperature {
        private float min;
        private float max;

        public float getMax() {
            return max;
        }

        public float getMin() {
            return min;
        }
    }

    @Override
    public String toString() {
        return new StringBuilder("List size: ")
                .append(list.size())
                .append(list.toString())
                .toString();
    }

    public ArrayList<ForecastData> getList() {
        return list;
    }
}
