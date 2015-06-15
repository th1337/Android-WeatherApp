package com.worldline.fpl.myweather.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.worldline.fpl.myweather.model.CityForecast;
import com.worldline.fpl.myweather.model.DayForecast;
import com.worldline.fpl.myweather.service.ForecastServiceApi;

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



    private static final String TAG ="RestClient" ;
    private static String baseUrl;
    @Inject protected ForecastServiceApi forecastService;


    @Inject protected Context context;
    @Inject protected Properties properties;

   // @Inject protected Properties properties;
    /** Instance unique pré-initialisée */
    private static RestClient INSTANCE ;

    /** Point d'accès pour l'instance unique du singleton
     * @param
    public static RestClient newInstance(Context context) throws IOException {




        if(INSTANCE==null)
        {
            INSTANCE=new RestClient(context);
        }
        else
        {
            INSTANCE.context=context.getApplicationContext();
        }


        return INSTANCE;
    }
    */

    @Inject
    public RestClient() {



        //baseUrl=properties.getProperty("app.forecast.baseurl");




      /**  OkHttpClient clientOK= new OkHttpClient();//the OkHttpClient
        // Create Cache
        Cache cache = null;

        cache = new Cache(new File(context.getCacheDir(), "http"), SIZE_OF_CACHE);

        clientOK.setCache(cache);

        String url="http://"+baseUrl;

        // Add Cache-Control Interceptor
        clientOK.networkInterceptors().add(mCacheControlInterceptor);

        // Create Executor
        Executor executor = Executors.newCachedThreadPool();


        Log.d("urlCalled", url);*/


       // forecastService = restAdapter.create(ForecastServiceApi.class);
    }

    public ForecastServiceApi getForecastService()
    {
        return forecastService;
    }


    public static boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public  boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    /**
     * Gets the 5 day / 3 hours forecast for the provided cityId
     * @param cityId
     * @param units
     * @return
     * @throws IOException
     */
    public List<DayForecast> getForecasts(long cityId,String units) throws IOException {


        List<DayForecast> result= null;


        try {
            CityForecast cityForecast = this.forecastService.getCityForecast(cityId, "metric");

            result = cityForecast.getListForecasts();
        }catch (Exception e)
        {
            throw new IOException("Network not available");
        }

        return result;

    }
}
