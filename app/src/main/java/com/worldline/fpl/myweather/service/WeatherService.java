package com.worldline.fpl.myweather.service;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.worldline.fpl.myweather.model.DayForecast;
import com.worldline.fpl.myweather.model.MainForecast;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/**
 * Created by a607937 on 09/06/2015.
 */
public class WeatherService {


    private WeatherService()
    {}

    /** Instance unique pré-initialisée */
    private static WeatherService INSTANCE = new WeatherService();

    /** Point d'accès pour l'instance unique du singleton */
    public static WeatherService newInstance()
    {	return INSTANCE;
    }



    public void loadDayForecast(Context context,ForecastLoadedInterface callback) throws IOException, ExecutionException, InterruptedException {

        LoadForecastAsyncTask asyncLoader=new LoadForecastAsyncTask(context,callback);



        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        //here we check the level api
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB){
            asyncLoader.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        } else{
            asyncLoader.execute();
        }





    }

}
