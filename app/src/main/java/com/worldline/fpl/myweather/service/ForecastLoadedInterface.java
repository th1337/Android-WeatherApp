package com.worldline.fpl.myweather.service;

import com.worldline.fpl.myweather.model.DayForecast;

import java.util.List;

/**
 * Created by a607937 on 09/06/2015.
 */
public interface ForecastLoadedInterface {

    public void ForecastsLoaded(List<DayForecast> forecasts);

}
