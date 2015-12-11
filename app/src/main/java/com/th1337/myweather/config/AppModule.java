package com.th1337.myweather.config;

import android.content.Context;


import com.th1337.myweather.WeatherApplication;
import com.th1337.myweather.tools.Tools;

import java.io.IOException;
import java.util.Properties;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by a607937 on 15/06/2015.
 */
@Module
public class AppModule {
    protected final WeatherApplication application;
    protected final Properties properties;

    public AppModule(WeatherApplication application, String property) throws IOException {
        this.application = application;

        this.properties = Tools.getProperties(application, property);


    }


    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    Properties provideProperties() {
        return properties;
    }
}
