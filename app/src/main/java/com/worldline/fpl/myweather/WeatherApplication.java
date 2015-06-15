package com.worldline.fpl.myweather;

import android.app.Application;

import com.worldline.fpl.myweather.config.AppModule;
import com.worldline.fpl.myweather.config.DaggerAppComponent;
import com.worldline.fpl.myweather.config.WeatherModule;
import com.worldline.fpl.myweather.config.AppComponent;


import java.io.IOException;

/**
 * Created by a607937 on 11/06/2015.
 */
public class WeatherApplication extends Application {

    private AppComponent dependencyGraph;

    public AppComponent getAppComponent()
    {
        return dependencyGraph;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            dependencyGraph = DaggerAppComponent
                    .builder()
                    .appModule(new AppModule(this, "app.properties"))
                    .weatherModule(new WeatherModule())
                    .build();
        } catch (IOException e) {
            // Should not happen
            throw new RuntimeException(e);
        }

    }
}
