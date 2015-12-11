package com.th1337.myweather;

import android.app.Application;

import com.th1337.myweather.config.AppComponent;
import com.th1337.myweather.config.AppModule;
import com.th1337.myweather.config.DaggerAppComponent;
import com.th1337.myweather.config.WeatherModule;



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
