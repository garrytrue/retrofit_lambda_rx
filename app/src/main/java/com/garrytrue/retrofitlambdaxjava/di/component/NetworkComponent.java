package com.garrytrue.retrofitlambdaxjava.di.component;

import com.garrytrue.retrofitlambdaxjava.ServiceLoader;
import com.garrytrue.retrofitlambdaxjava.di.modules.NetworkModule;
import com.garrytrue.retrofitlambdaxjava.mvp.presenters.WeatherFragmentPresenter;

import javax.inject.Singleton;

import dagger.Component;
import retrofit.Retrofit;

@Singleton
@Component(modules = NetworkModule.class)

public interface NetworkComponent {

    Retrofit provideRetrofit();

    void inject(WeatherFragmentPresenter presenter);
    void inject(ServiceLoader loader);
}
