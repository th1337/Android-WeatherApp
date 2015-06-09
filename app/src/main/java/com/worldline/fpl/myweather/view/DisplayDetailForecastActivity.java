package com.worldline.fpl.myweather.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.worldline.fpl.myweather.R;
import com.worldline.fpl.myweather.model.DayForecast;

/**
 * Created by a607937 on 09/06/2015.
 */
public class DisplayDetailForecastActivity extends FragmentActivity{


    public static final String KEY_DAY_FORECAST ="DayForecast" ;//the key to get the current forecast in Bundle

    public static Intent newIntent(Context context, DayForecast forecast) {
        Intent intent = new Intent(context, DisplayDetailForecastActivity.class);

        intent.putExtra(KEY_DAY_FORECAST, forecast);

        return intent;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forecast_detail);
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            DayForecast forecast = (DayForecast) getIntent().getParcelableExtra(KEY_DAY_FORECAST);

            // Create a new Fragment to be placed in the activity layout
            //then we put the DayForecast in the Bundle
            DisplayDetailForecastFragment firstFragment = DisplayDetailForecastFragment.newInstance(forecast);



            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();


        }




    }

}
