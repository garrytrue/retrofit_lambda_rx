package com.garrytrue.retrofitlambdaxjava.di.modules;


import android.app.Application;

import com.garrytrue.retrofitlambdaxjava.R;
import com.garrytrue.retrofitlambdaxjava.RetrofitLambdaXJavaApp;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

@Module
public class NetworkModule {
    private static final int HTTP_CACHE = 10 * 1024 * 1024;
    private Application application;


    @Inject
    public NetworkModule(RetrofitLambdaXJavaApp application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(application.getString(R.string.base_url))
                .client(new OkHttpClient().setCache(new Cache(application.getCacheDir(), HTTP_CACHE)))
                .build();
    }
}
