package com.worldline.fpl.myweather.config;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.worldline.fpl.myweather.BuildConfig;
import com.worldline.fpl.myweather.service.ForecastServiceApi;
import com.worldline.fpl.myweather.tools.CacheInterceptor;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by a607937 on 15/06/2015.
 */
@Module
public class WeatherModule {

    private static long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MB




    @Provides
    @Singleton
    Gson provideGson() {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        return gson;

    }


    @Provides
    @Singleton
    RestAdapter provideRestAdapter(OkHttpClient client, Gson gson, Properties properties) {


        Executor executor = Executors.newCachedThreadPool();

        String url = "http://" + properties.getProperty("app.forecast.baseurl");


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setEndpoint(url)
                .setExecutors(executor, executor)
                .setClient(new OkClient(client))
                .setConverter(new GsonConverter(gson))
                .build();

        return restAdapter;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Context context) {
        OkHttpClient clientOK = new OkHttpClient();//the OkHttpClient
        // Create Cache
        Cache cache = null;

        cache = new Cache(new File(context.getCacheDir(), "http"), SIZE_OF_CACHE);

        clientOK.setCache(cache);


        // Add Cache-Control Interceptor
        clientOK.networkInterceptors().add(new CacheInterceptor());


        return clientOK;
    }


    @Provides
    @Singleton
    ForecastServiceApi provideForecastServiceApi(RestAdapter restAdapter) {

        return restAdapter.create(ForecastServiceApi.class);
    }


}
