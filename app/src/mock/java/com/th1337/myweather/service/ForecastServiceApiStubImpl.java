package com.worldline.fpl.myweather.service;

import com.worldline.fpl.myweather.model.CityForecast;
import com.worldline.fpl.myweather.model.Cloud;
import com.worldline.fpl.myweather.model.DayForecast;
import com.worldline.fpl.myweather.model.MainForecast;
import com.worldline.fpl.myweather.model.Weather;
import com.worldline.fpl.myweather.model.Wind;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by a607937 on 16/06/2015.
 */
@Singleton
public class ForecastServiceApiStubImpl implements ForecastServiceApi {

    @Inject
    public ForecastServiceApiStubImpl() {

    }

    @Override
    public CityForecast getCityForecast(long id, String units) {

        List<DayForecast> result = new ArrayList<>();

        Weather weather = new Weather(800l, "Sunny", "today weather is perfect", "10d");
        Weather weather1 = new Weather(8002, "Rain", "It's raining men, alleluja", "10n");
        List<Weather> lw = new ArrayList<>();
        List<Weather> lw1 = new ArrayList<>();
        lw.add(weather);
        lw1.add(weather1);

        DayForecast day = new DayForecast(new Date(), lw,
                new MainForecast(27.5f, 28.5f, 30.5f, 998, 998, 887, 60, 0.5f), new Cloud(44),
                new Wind(20.7f, 340.5f));
        DayForecast day1 = new DayForecast(new Date(), lw1,
                new MainForecast(27.5f, 28.5f, 30.5f, 998, 998, 887, 60, 0.5f), new Cloud(35),
                new Wind(20.7f, 340.5f));

        for (int i = 0; i < 35; i++) {
            result.add(day);
            result.add(day1);
        }
        /**try {
         CityForecast cityForecast = this.forecastService.getCityForecast(cityId, "metric");

         result = cityForecast.getListForecasts();
         } catch (Exception e) {
         throw new IOException("Network not available");
         }*/

        CityForecast response = new CityForecast(result);

        return response;
    }
}
