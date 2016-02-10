package com.garrytrue.retrofitlambdaxjava.di.component;

import com.garrytrue.retrofitlambdaxjava.mvp.views.WeatherFragment;
import com.garrytrue.retrofitlambdaxjava.di.scope.ActivityScope;
import com.garrytrue.retrofitlambdaxjava.di.modules.MainActivityModule;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = MainActivityModule.class)
public interface MainActivityComponent {

      void inject(WeatherFragment weatherFragment);
}