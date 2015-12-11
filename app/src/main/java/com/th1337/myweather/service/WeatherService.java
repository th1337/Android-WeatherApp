package com.th1337.myweather.service;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;


import com.th1337.myweather.tools.RestClient;

import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by a607937 on 09/06/2015.
 */
@Singleton
public class WeatherService {

    @Inject
    protected RestClient restClient;
    @Inject
    protected Context context;

    @Inject
    public WeatherService() {

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void loadDayForecast(ForecastLoadedInterface callback, Long cityId) throws ExecutionException, InterruptedException {


        LoadForecastAsyncTask asyncLoader = new LoadForecastAsyncTask(restClient, context, callback);


        Long[] params = {cityId};
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        //here we check the level api
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            asyncLoader.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);
        } else {
            asyncLoader.execute(params);
        }


    }

}
