package com.garrytrue.retrofitlambdaxjava;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.garrytrue.retrofitlambdaxjava.events.EventComplete;
import com.garrytrue.retrofitlambdaxjava.events.EventError;
import com.garrytrue.retrofitlambdaxjava.events.EventReceiveCurrentWeather;
import com.garrytrue.retrofitlambdaxjava.events.EventReceiveWeatherForecast;
import com.garrytrue.retrofitlambdaxjava.mvp.models.CurrentWeatherModel;
import com.garrytrue.retrofitlambdaxjava.mvp.models.WeatherForecastModel;
import com.garrytrue.retrofitlambdaxjava.retrofitInterfaces.WeatherRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import retrofit.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;


public class ServiceLoader extends IntentService {
    private static final String TAG = ServiceLoader.class.getSimpleName();
    private static final String ACTION_LOAD_DATA = "ACTION_LOAD_DATA";
    private static final String LOCATION_BUNDLE_KEY = "LOCATION_BUNDLE_KEY";
    private CompositeSubscription mCompositeSubscription;
    private WeatherRequest mWeatherRequest;
    @Inject
    protected Retrofit retrofit;

    public ServiceLoader() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitLambdaXJavaApp.getApplication(RetrofitLambdaXJavaApp.getContextApplication())
                .getNetworkComponent().inject(this);
        mCompositeSubscription = new CompositeSubscription();
    }

    public static Intent makeIntent(Context context, String location) {
        Intent intent = new Intent(context, ServiceLoader.class);
        intent.setAction(ACTION_LOAD_DATA);
        intent.putExtra(LOCATION_BUNDLE_KEY, location);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.getAction().equals(ACTION_LOAD_DATA) && !TextUtils.isEmpty(intent.getStringExtra(LOCATION_BUNDLE_KEY))) {
            final String location = intent.getStringExtra(LOCATION_BUNDLE_KEY);
            mWeatherRequest = retrofit.create(WeatherRequest.class);
            mCompositeSubscription.add(fetchCurrentWeather(location)
                    .subscribe(new Subscriber<CurrentWeatherModel>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "onCompleted: ");
                            postEventComplete(EventComplete.TASK_CURRENT_WEATHER);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "onError: " + e);
                            postEventError(EventComplete.TASK_CURRENT_WEATHER);
                        }

                        @Override
                        public void onNext(CurrentWeatherModel currentWeatherModel) {
                            Log.d(TAG, "onNext: CurrentWeatherModel " + currentWeatherModel);
                            EventBus.getDefault().post(new EventReceiveCurrentWeather(currentWeatherModel));
                        }
                    }));
            mCompositeSubscription.add(fetchForecast(location)
                    .subscribe(new Subscriber<WeatherForecastModel>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "onCompleted: Forecast");
                            postEventComplete(EventComplete.TASK_FORECAST);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "onError: Forecast " + e);
                            e.printStackTrace();
                            postEventError(EventComplete.TASK_FORECAST);
                        }

                        @Override
                        public void onNext(WeatherForecastModel weatherForecastModel) {
                            Log.d(TAG, "onNext: Forecast " + weatherForecastModel);
                            EventBus.getDefault().post(new EventReceiveWeatherForecast(weatherForecastModel));
                        }
                    }));
        }
    }

    private Observable<CurrentWeatherModel> fetchCurrentWeather(final String location) {

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(WeatherRequest.Q_KEY, location);
        queryMap.put(WeatherRequest.APP_ID_KEY, WeatherRequest.APP_ID_VALUE);
        queryMap.put(WeatherRequest.UNITS_KEY, WeatherRequest.UNITS_VALUE);
        return mWeatherRequest.fetchCurrentWeather(queryMap)
                .flatMap(currentWeatherModel -> currentWeatherModel.filterWebServiceErrors());
    }

    private Observable<WeatherForecastModel> fetchForecast(final String location) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(WeatherRequest.Q_KEY, location);
        queryMap.put(WeatherRequest.APP_ID_KEY, WeatherRequest.APP_ID_VALUE);
        queryMap.put(WeatherRequest.UNITS_KEY, WeatherRequest.UNITS_VALUE);
        return mWeatherRequest.fetchWeatherForecasts(queryMap)
                .flatMap(currentWeatherModel -> currentWeatherModel.filterWebServiceErrors());
    }

    private void postEventComplete(int flag) {
        EventBus.getDefault().post(new EventComplete(flag));
    }

    private void postEventError(int flag) {
        EventBus.getDefault().post(new EventError(flag));
    }
}
