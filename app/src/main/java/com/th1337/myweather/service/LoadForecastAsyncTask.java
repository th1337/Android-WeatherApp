package com.th1337.myweather.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.th1337.myweather.model.DayForecast;
import com.th1337.myweather.tools.RestClient;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by a607937 on 09/06/2015.
 */
public class LoadForecastAsyncTask extends AsyncTask<Long, Void, List<DayForecast>> {

    public static final String TAG = "LoadForecastAsyncTask";

    protected RestClient client;


    private WeakReference<ForecastLoadedInterface> mCallBack;
    private Context context;
    private Exception pendingException;

    public LoadForecastAsyncTask(RestClient client, Context context, ForecastLoadedInterface callBack) {
        mCallBack = new WeakReference<ForecastLoadedInterface>(callBack);
        this.context = context;
        this.client = client;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected List<DayForecast> doInBackground(Long... cityId) {

        List<DayForecast> result = null;
        try {

            result = client.getForecasts(cityId[0], "metric");
        } catch (IOException e) {

            pendingException = e;
            Log.e(TAG, "error during task", e);
        }

        return result;
    }

    @Override
    protected void onPostExecute(List<DayForecast> result) {

        ForecastLoadedInterface ref = mCallBack.get();
        if (ref == null || !ref.isValid()) {
            return;
        }

        if (pendingException == null) {
            ref.onForecastsLoaded(result);
        } else {
            ref.onForecastLoadingError();
        }
    }


}