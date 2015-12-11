package com.th1337.myweather.config;


import com.th1337.myweather.view.DisplayForecastFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by a607937 on 15/06/2015.
 */
@Singleton
@Component(modules = {WeatherModule.class, AppModule.class})
public interface AppComponent {

    void inject(DisplayForecastFragment forecastFragment);
}