package com.worldline.fpl.myweather.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by a607937 on 11/06/2015.
 */
public class CityForecast {

    @SerializedName("list")
    private List<DayForecast> listForecasts;

    public CityForecast(List<DayForecast> listForecasts) {
        this.listForecasts = listForecasts;
    }

    public List<DayForecast> getListForecasts() {
        return listForecasts;
    }

    public void setListForecasts(List<DayForecast> listForecasts) {
        this.listForecasts = listForecasts;
    }
}
