package com.garrytrue.retrofitlambdaxjava.di.modules;

import com.garrytrue.retrofitlambdaxjava.mvp.presenters.WeatherFragmentPresenter;

import dagger.Module;
import dagger.Provides;

@SuppressWarnings("WeakerAccess")
@Module
public class WeatherFragmentModule {
    @Provides
    WeatherFragmentPresenter provideWeatherFragmentPresenter(){
        return new WeatherFragmentPresenter();
    }
}
