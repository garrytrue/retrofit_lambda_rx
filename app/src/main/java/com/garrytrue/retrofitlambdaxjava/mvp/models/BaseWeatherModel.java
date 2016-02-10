package com.garrytrue.retrofitlambdaxjava.mvp.models;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import rx.Observable;


public class BaseWeatherModel {
    private static final String TAG = BaseWeatherModel.class.getSimpleName();
    @SerializedName("cod")
    private int httpCode;

    @SuppressWarnings("unused")
    public class Weather {
        public String description;
        public String icon;

        @Override
        public String toString() {
            return new StringBuilder("Desc ").append(description).append(" Icon ").append(icon).toString();
        }

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }
    }

    /**
     * The web service always returns a HTTP header code of 200 and communicates errors
     * through a 'cod' field in the JSON payload of the response body.
     */
    public Observable filterWebServiceErrors() {
        Log.d(TAG, "filterWebServiceErrors: " + httpCode);
        if (httpCode == 200) {
            return Observable.just(this);
        } else {
            return Observable.error(
                    new RuntimeException("There was a problem fetching the weather data."));
        }
    }
}
