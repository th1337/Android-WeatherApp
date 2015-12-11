package com.th1337.myweather.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.th1337.myweather.model.CityForecast;
import com.th1337.myweather.model.DayForecast;
import com.th1337.myweather.service.ForecastServiceApi;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by a607937 on 11/06/2015.
 */
@Singleton
public class RestClient {


    private static final String TAG = "RestClient";
    @Inject
    protected ForecastServiceApi forecastService;


    @Inject
    protected Context context;
    @Inject
    protected Properties properties;


    @Inject
    public RestClient() {
    }

    public ForecastServiceApi getForecastService() {
        return forecastService;
    }


    public static boolean isConnected(Context context) {
        ConnectivityManager cm
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();

    }

    /**
     * Gets the 5 day / 3 hours forecast for the provided cityId
     *
     * @param cityId
     * @param units
     * @return
     * @throws IOException
     */
    public List<DayForecast> getForecasts(long cityId, String units) throws IOException {


        List<DayForecast> result = null;
        String apiKey = properties.getProperty("app.forecast.apikey");
        try {
            CityForecast cityForecast = this.forecastService.getCityForecast(cityId, "metric", apiKey);

            result = cityForecast.getListForecasts();
        } catch (Exception e) {
            throw new IOException("Network not available");
        }

        return result;

    }
}