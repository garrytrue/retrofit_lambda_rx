package com.garrytrue.retrofitlambdaxjava;

import android.app.Application;
import android.content.Context;

import com.garrytrue.retrofitlambdaxjava.di.component.ApplicationComponent;
import com.garrytrue.retrofitlambdaxjava.di.component.DaggerApplicationComponent;
import com.garrytrue.retrofitlambdaxjava.di.modules.ApplicationModule;
import com.garrytrue.retrofitlambdaxjava.di.component.NetworkComponent;
import com.garrytrue.retrofitlambdaxjava.di.modules.NetworkModule;
import com.garrytrue.retrofitlambdaxjava.di.component.DaggerNetworkComponent;

public class RetrofitLambdaXJavaApp extends Application {
    private ApplicationComponent applicationComponent;
    private static Context mAppContext;
    private NetworkComponent networkComponent;

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }


    public static RetrofitLambdaXJavaApp getApplication(Context context) {
        return (RetrofitLambdaXJavaApp) context.getApplicationContext();
    }


    public static Context getContextApplication() {
        return mAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new
                ApplicationModule(this)).build();
        applicationComponent.inject(this);
        networkComponent = DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule(this)).build();
    }
}
