package com.th1337.myweather.service;



import com.th1337.myweather.model.DayForecast;

import java.util.List;

/**
 * Created by a607937 on 09/06/2015.
 */
public interface ForecastLoadedInterface {

    public void onForecastsLoaded(List<DayForecast> forecasts);
    public void onForecastLoadingError();

    boolean isValid();
}
