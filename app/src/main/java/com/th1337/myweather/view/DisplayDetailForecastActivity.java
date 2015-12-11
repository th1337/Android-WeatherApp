package com.th1337.myweather.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.th1337.myweather.model.City;
import com.th1337.myweather.model.DayForecast;
import com.th1337.myweather.R;

/**
 * Created by a607937 on 09/06/2015.
 */
public class DisplayDetailForecastActivity extends BaseActivity {


    public static final String KEY_DAY_FORECAST = "DayForecast"; //the key to get the current forecast in Bundle
    private static final String KEY_CITY = "city";

    public static Intent newIntent(Context context, DayForecast forecast, City city) {
        Intent intent = new Intent(context, DisplayDetailForecastActivity.class);

        intent.putExtra(KEY_DAY_FORECAST, forecast);
        intent.putExtra(KEY_CITY, city);
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
            City city = (City) getIntent().getParcelableExtra(KEY_CITY);

            // Create a new Fragment to be placed in the activity layout
            //then we put the DayForecast in the Bundle
            DisplayDetailForecastFragment firstFragment = DisplayDetailForecastFragment.newInstance(forecast, city);


            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);


        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, DisplayForecastActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                break;
            default:
                break;

        }
        return (super.onOptionsItemSelected(menuItem));
    }


}
