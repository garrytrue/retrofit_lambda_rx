package com.garrytrue.retrofitlambdaxjava.mvp.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.garrytrue.retrofitlambdaxjava.R;
import com.garrytrue.retrofitlambdaxjava.RetrofitLambdaXJavaApp;
import com.garrytrue.retrofitlambdaxjava.di.component.DaggerMainActivityComponent;
import com.garrytrue.retrofitlambdaxjava.di.component.MainActivityComponent;
import com.garrytrue.retrofitlambdaxjava.di.modules.MainActivityModule;

public class MainActivity extends AppCompatActivity {
    private MainActivityComponent mainActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupComponent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> updateWeatherData());
    }

    private void setupComponent() {
        mainActivityComponent = DaggerMainActivityComponent.builder()
                .applicationComponent(RetrofitLambdaXJavaApp.getApplication(this).getApplicationComponent())
                .mainActivityModule(new MainActivityModule())
                .build();

    }


    private void updateWeatherData() {
        final WeatherFragment fragment = (WeatherFragment) getFragmentManager().findFragmentById(R.id.fragment);
        if (!fragment.isTasksBusy()) {
            fragment.fetchData();
        }
    }

    public MainActivityComponent getMainActivityComponent() {
        return mainActivityComponent;
    }
}
