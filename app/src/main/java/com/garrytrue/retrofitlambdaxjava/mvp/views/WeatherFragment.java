package com.garrytrue.retrofitlambdaxjava.mvp.views;

import android.app.Fragment;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.garrytrue.retrofitlambdaxjava.R;
import com.garrytrue.retrofitlambdaxjava.RetrofitLambdaXJavaApp;
import com.garrytrue.retrofitlambdaxjava.adapters.ForecastAdapter;
import com.garrytrue.retrofitlambdaxjava.mvp.models.CurrentWeatherModel;
import com.garrytrue.retrofitlambdaxjava.mvp.models.WeatherForecastModel;
import com.garrytrue.retrofitlambdaxjava.mvp.presenters.WeatherFragmentPresenter;
import com.garrytrue.retrofitlambdaxjava.utils.Utils;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;


public class WeatherFragment extends Fragment implements IWeatherFragmentView {
    private static final String TAG = WeatherFragment.class.getSimpleName();
    @Inject
    WeatherFragmentPresenter mPresenter;

    private View mProgressBar;
    private EditText mEditText;
    private TextView mLocation, mTemperature;
    private ImageView mWeatherIcon;
    private ForecastAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initUI(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).getMainActivityComponent().inject(this);
        RetrofitLambdaXJavaApp.getApplication(getActivity()).getNetworkComponent().inject(mPresenter);
        mPresenter.init(this);
        mPresenter.restoreState(savedInstanceState);
    }

    private void initUI(View view) {
        mProgressBar = view.findViewById(R.id.progressBar);
        mEditText = ((EditText) view.findViewById(R.id.locationQuery));
        mLocation = (TextView) view.findViewById(R.id.tv_location);
        mTemperature = (TextView) view.findViewById(R.id.tv_temperature);
        mWeatherIcon = (ImageView) view.findViewById(R.id.im_weather);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = ForecastAdapter.createForecastAdapter(new WeatherForecastModel());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mPresenter.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
       mPresenter.onStop();
        super.onStop();
    }

    @Override
    public void updateForecastView(WeatherForecastModel forecastModel) {
        Log.d(TAG, "updateForecastView: " + forecastModel);
        mAdapter.update(forecastModel);

    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void updateHeader(CurrentWeatherModel model) {
        mLocation.setText(model.getLocationName());
        mTemperature.setText(Utils.formatTemperature(model.getMain().getTemp()));
        Picasso.with(getActivity())
                .load(String.format(getString(R.string.image_endpoint), model.getWeather().getIcon()))
                .into(mWeatherIcon);
    }

    @Override
    public void showError() {
        Utils.showToast(getActivity(), getString(R.string.msg_error));
    }

    public void fetchData() {
        Utils.hideKeyboard(getActivity(), mEditText.getWindowToken());

        final String location =
                Utils.uppercaseInput(getActivity(),
                        mEditText.getText().toString().trim()
                );
        if (!TextUtils.isEmpty(location)) {
            mPresenter.startFetchData(location);
            mPresenter.showProgress();
        }
    }
    public boolean isTasksBusy(){
        if(mPresenter.isTasksBusy()){
            Utils.showToast(getActivity(), getString(R.string.msg_wait));
        }
        return mPresenter.isTasksBusy();
    }
}
