package com.garrytrue.retrofitlambdaxjava.di.component;

import com.garrytrue.retrofitlambdaxjava.RetrofitLambdaXJavaApp;
import com.garrytrue.retrofitlambdaxjava.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(RetrofitLambdaXJavaApp application);
}