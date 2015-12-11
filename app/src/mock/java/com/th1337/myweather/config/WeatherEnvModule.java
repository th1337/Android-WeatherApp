package com.worldline.fpl.myweather.config;

import com.worldline.fpl.myweather.service.ForecastServiceApi;
import com.worldline.fpl.myweather.service.ForecastServiceApiStubImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by a607937 on 16/06/2015.
 */
@Module
public class WeatherEnvModule {

    @Provides
    @Singleton
    ForecastServiceApi provideForecastApi(ForecastServiceApiStubImpl impl) {
        return impl;
    }

}
