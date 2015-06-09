package com.worldline.fpl.myweather.service;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.worldline.fpl.myweather.model.DayForecast;
import com.worldline.fpl.myweather.tools.Tools;

import java.io.IOException;
import java.util.List;

/**
 * Created by a607937 on 09/06/2015.
 */
public class LoadForecastAsyncTask extends AsyncTask<Void, Void, List<DayForecast>>
{

    ForecastLoadedInterface mCallBack;

    public LoadForecastAsyncTask(Context context,ForecastLoadedInterface callBack)
    {
        mCallBack=callBack;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onProgressUpdate(Void... values){
        super.onProgressUpdate(values);

    }

    @Override
    protected List<DayForecast> doInBackground(Void... arg0) {

        String url = "http://api.openweathermap.org/data/2.5/weather?id=2996944";//TODO change it !
        try {
            Tools.sendGet(url);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(List<DayForecast> result) {
        mCallBack.ForecastsLoaded(result);
    }







}