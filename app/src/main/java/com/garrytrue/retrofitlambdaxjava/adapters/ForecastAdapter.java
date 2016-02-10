package com.garrytrue.retrofitlambdaxjava.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.garrytrue.retrofitlambdaxjava.R;
import com.garrytrue.retrofitlambdaxjava.mvp.models.WeatherForecastModel;
import com.garrytrue.retrofitlambdaxjava.utils.Utils;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastHolder> {
    private static final String TAG = ForecastAdapter.class.getSimpleName();
    private WeatherForecastModel forecastModel;

    private ForecastAdapter(WeatherForecastModel forecastModel) {
        this.forecastModel = forecastModel;
    }

    public static ForecastAdapter createForecastAdapter(WeatherForecastModel forecastModel) {
        return new ForecastAdapter(forecastModel);
    }

    @Override
    public ForecastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_forecast_list_item, parent, false);
        return ForecastHolder.createForecastHolder(item);
    }

    @Override
    public void onBindViewHolder(ForecastHolder holder, int position) {
        WeatherForecastModel.ForecastData data = forecastModel.getList().get(position);
        Log.d(TAG, "onBindViewHolder: TimeStamp " + Utils.getDayOfWeek(data.getTimeStamp()));
        holder.setDate(Utils.getDayOfWeek(data.getTimeStamp()));
        holder.setDescription(data.getWeather().getDescription());
        holder.setMaxTemp(Utils.formatTemperature(data.getTemp().getMax()));
        holder.setMinTemp(Utils.formatTemperature(data.getTemp().getMin()));
        holder.setIcon(data.getWeather().getIcon());
    }

    @Override
    public int getItemCount() {
        return forecastModel.getList().size();
    }
    public void update(WeatherForecastModel model){
        Log.d(TAG, "update: " + model);
        forecastModel = model;
        notifyDataSetChanged();
    }
}

