package com.garrytrue.retrofitlambdaxjava.di.modules;

import com.garrytrue.retrofitlambdaxjava.RetrofitLambdaXJavaApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final RetrofitLambdaXJavaApp application;

    public ApplicationModule(RetrofitLambdaXJavaApp application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public RetrofitLambdaXJavaApp provideRetrofitLambdaXJavaApp() {
        return application;
    }

    @Singleton
    @Provides
    public NetworkModule provideNetworkModule() {
        return new NetworkModule(application);
    }
}

