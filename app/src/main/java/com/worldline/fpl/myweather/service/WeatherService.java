package com.worldline.fpl.myweather.service;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.worldline.fpl.myweather.tools.RestClient;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by a607937 on 09/06/2015.
 */
@Singleton
public class WeatherService {

    @Inject protected RestClient restClient;
    @Inject protected Context context;

    @Inject
    public WeatherService() {

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void loadDayForecast(ForecastLoadedInterface callback,Long cityId) throws IOException, ExecutionException, InterruptedException {



        //get the properties and the base URL
       // Properties properties= Tools.getProperties(context,"app.properties");

        //String baseUrl=properties.getProperty("app.forecast.baseurl")+"forecast?id="+Long.toString(cityId)+"&units=metric";




        LoadForecastAsyncTask asyncLoader=new LoadForecastAsyncTask(restClient,context,callback);


        Long[] params = { cityId };
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        //here we check the level api
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB){
            asyncLoader.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,params);
        } else{
            asyncLoader.execute(params);
        }





    }

}
