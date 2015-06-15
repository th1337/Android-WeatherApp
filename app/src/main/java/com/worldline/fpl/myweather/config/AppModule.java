package com.worldline.fpl.myweather.config;

import android.content.Context;

import com.worldline.fpl.myweather.WeatherApplication;
import com.worldline.fpl.myweather.tools.Tools;

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

        this.properties= Tools.getProperties(application,property);


    }


    @Provides
    @Singleton
    Context provideContext()
    {
        return application;
    }

    @Provides
    @Singleton
    Properties provideProperties()
    {
        return properties;
    }
}
