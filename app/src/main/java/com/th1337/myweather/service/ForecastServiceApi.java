package com.th1337.myweather.service;



import com.th1337.myweather.model.CityForecast;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by a607937 on 11/06/2015.
 */
public interface ForecastServiceApi {

    @GET("/forecast")
    public CityForecast getCityForecast(@Query("id") long id, @Query("units") String units, @Query("APPID") String appID);


}
