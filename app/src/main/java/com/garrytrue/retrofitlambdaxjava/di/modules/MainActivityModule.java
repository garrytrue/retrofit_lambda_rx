package com.garrytrue.retrofitlambdaxjava.di.modules;

import com.garrytrue.retrofitlambdaxjava.mvp.presenters.WeatherFragmentPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {
    @Provides
    WeatherFragmentPresenter provideWeatherFragmentPresenter() {
        return new WeatherFragmentPresenter();
    }
}