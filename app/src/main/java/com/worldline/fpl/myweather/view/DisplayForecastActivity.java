package com.worldline.fpl.myweather.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.worldline.fpl.myweather.R;
import com.worldline.fpl.myweather.model.DayForecast;

/**
 * Created by a607937 on 08/06/2015.
 */
public class DisplayForecastActivity extends FragmentActivity implements DisplayForecastFragment.OnHeadlineSelectedListener{

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_list);

        // However, if we're being restored from a previous state,
        // then we don't need to do anything and should return or else
        // we could end up with overlapping fragments.
//        if (savedInstanceState != null) {
//            return;
//        }

        DisplayForecastFragment listFragment = (DisplayForecastFragment) getSupportFragmentManager().findFragmentByTag(DisplayForecastFragment.TAG);

        if (listFragment == null) {
            listFragment = DisplayForecastFragment.newInstance();
        }

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, listFragment, DisplayForecastFragment.TAG)
                .commit();


    }


    @Override
    public void onForecastSelected(DayForecast selectedForecast) {


        Toast.makeText(this,"current :"+selectedForecast.getWeatherForecast().getDescription(),Toast.LENGTH_LONG).show();

        Intent intent = DisplayDetailForecastActivity.newIntent(this, selectedForecast);

        startActivity(intent);
    }
}
