package com.garrytrue.retrofitlambdaxjava.mvp.presenters;

import android.os.Bundle;
import android.util.Log;

import com.garrytrue.retrofitlambdaxjava.RetrofitLambdaXJavaApp;
import com.garrytrue.retrofitlambdaxjava.ServiceLoader;
import com.garrytrue.retrofitlambdaxjava.events.EventComplete;
import com.garrytrue.retrofitlambdaxjava.events.EventError;
import com.garrytrue.retrofitlambdaxjava.events.EventReceiveCurrentWeather;
import com.garrytrue.retrofitlambdaxjava.events.EventReceiveWeatherForecast;
import com.garrytrue.retrofitlambdaxjava.mvp.models.CurrentWeatherModel;
import com.garrytrue.retrofitlambdaxjava.mvp.models.WeatherForecastModel;
import com.garrytrue.retrofitlambdaxjava.mvp.views.IWeatherFragmentView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import retrofit.Retrofit;

public class WeatherFragmentPresenter implements IWeatherFragmentPresenter {
    private static final String TAG = WeatherFragmentPresenter.class.getSimpleName();
    private static final String CURRENT_WEATHER_TAG = "CURRENT_WEATHER_TAG";
    private static final String WEATHER_FORECAST_TAG = "WEATHER_FORECAST_TAG";
    private IWeatherFragmentView mView;
    private CurrentWeatherModel mCurrentWeatherModel;
    private WeatherForecastModel mWeatherForecastModel;
    private static boolean isWeatherTaskWork = false;
    private static boolean isForecastTaskWork = false;
    @Inject
    protected Retrofit retrofit;


    @Override
    public void init(IWeatherFragmentView view) {
        this.mView = view;
    }

    @Override
    public void updateForecast(WeatherForecastModel forecastModel) {
        Log.d(TAG, "updateForecast: ");
        mView.updateForecastView(forecastModel);
    }

    @Override
    public void showProgress() {
        mView.showProgress();
    }

    @Override
    public void hideProgress() {
        mView.hideProgress();
    }

    @Override
    public void updateHeader(CurrentWeatherModel model) {
        mView.updateHeader(model);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void showError() {
        mView.showError();
    }

    @Override
    public boolean isTasksBusy() {
        return isForecastTaskWork && isWeatherTaskWork;
    }

    @Override
    public void saveState(Bundle outState) {
        if (mCurrentWeatherModel != null && mWeatherForecastModel != null) {
            outState.putSerializable(CURRENT_WEATHER_TAG, mCurrentWeatherModel);
            outState.putSerializable(WEATHER_FORECAST_TAG, mWeatherForecastModel);
        }
    }

    @Override
    public void restoreState(Bundle savedState) {
        if (savedState != null && savedState.containsKey(CURRENT_WEATHER_TAG) && savedState.containsKey(WEATHER_FORECAST_TAG)) {
            mWeatherForecastModel = (WeatherForecastModel) savedState.getSerializable(WEATHER_FORECAST_TAG);
            mCurrentWeatherModel = (CurrentWeatherModel) savedState.getSerializable(CURRENT_WEATHER_TAG);
            updateHeader(mCurrentWeatherModel);
            updateForecast(mWeatherForecastModel);


        }

    }

    @Override
    public void startFetchData(String location) {
        Log.d(TAG, "startFetchData: BaseUrl " + retrofit.baseUrl().url());
        isForecastTaskWork = isWeatherTaskWork = true;
        RetrofitLambdaXJavaApp.getContextApplication()
                .startService(ServiceLoader.makeIntent(RetrofitLambdaXJavaApp.getContextApplication(), location));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventReceiveCurrentWeather event) {
        Log.d(TAG, "onEventMainThread: Receive EventReceiveCurrentWeather");
        hideProgress();
        updateHeader(event.getModel());
        mCurrentWeatherModel = event.getModel();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventReceiveWeatherForecast event) {
        Log.d(TAG, "onEventMainThread: Receive EventReceiveCurrentWeather");
        hideProgress();
        updateForecast(event.getModel());
        mWeatherForecastModel = event.getModel();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventComplete event) {
        Log.d(TAG, "onEventMainThread: Receive EventComplete. Flag " + event.getFlag());
        hideProgress();
        if (event.getFlag() == EventComplete.TASK_CURRENT_WEATHER) {
            isWeatherTaskWork = false;
        } else {
            isForecastTaskWork = false;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventError event) {
        Log.d(TAG, "onEventMainThread: Receive EventError. Flag " + event.getFlag());
        hideProgress();
        showError();
        if (event.getFlag() == EventComplete.TASK_CURRENT_WEATHER) {
            isWeatherTaskWork = false;
        } else {
            isForecastTaskWork = false;
        }
    }
}
