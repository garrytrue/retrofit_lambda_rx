package com.garrytrue.retrofitlambdaxjava.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.garrytrue.retrofitlambdaxjava.R;
import com.squareup.picasso.Picasso;


public class ForecastHolder extends RecyclerView.ViewHolder {
    private TextView mDay, mDescription, mMaxTemp, mMinTemp;
    private ImageView mWeatherIcon;
    private ForecastHolder(View itemView) {
        super(itemView);
        initView(itemView);
    }

    private void initView(View itemView) {
        mDay = (TextView) itemView.findViewById(R.id.day);
        mDescription = (TextView) itemView.findViewById(R.id.description);
        mMaxTemp = (TextView) itemView.findViewById(R.id.maximum_temperature);
        mMinTemp = (TextView) itemView.findViewById(R.id.minimum_temperature);
        mWeatherIcon = (ImageView) itemView.findViewById(R.id.im_list_weather);

    }

    public static ForecastHolder createForecastHolder(View itemView) {
        return new ForecastHolder(itemView);
    }

    public void setDescription(String description) {
        mDescription.setText(description);
    }
    public void setDate(String date){
        mDay.setText(date);
    }
    public void setMaxTemp(String maxTemp){
        mMaxTemp.setText(maxTemp);
    }
    public void setMinTemp(String minTemp){
        mMinTemp.setText(minTemp);
    }
    public void setIcon(String icon){
        final Context context = mWeatherIcon.getContext();
        Picasso.with(context)
                .load(String.format(context.getString(R.string.image_endpoint), icon))
                .into(mWeatherIcon);
    }
}

