package com.th1337.myweather.config;



import com.th1337.myweather.service.ForecastServiceApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Created by a607937 on 16/06/2015.
 */
@Module
public class WeatherEnvModule {

    @Provides
    @Singleton
    ForecastServiceApi provideForecastServiceApi(RestAdapter restAdapter) {

        return restAdapter.create(ForecastServiceApi.class);
    }

}
