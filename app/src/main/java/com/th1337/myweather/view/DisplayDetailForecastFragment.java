package com.th1337.myweather.view;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.th1337.myweather.model.City;
import com.th1337.myweather.model.DayForecast;
import com.th1337.myweather.tools.Tools;
import com.th1337.myweather.R;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by a607937 on 09/06/2015.
 */
public class DisplayDetailForecastFragment extends Fragment {

    public static final String TAG = "DisplayDetailForecastFragment";
    private static final String KEY_DAY_FORECAST = "DayForecast"; //the key to get the current forecast in Bundle
    private static final String CURRENT_DETAIL = "forecast";
    private static final String KEY_CITY = "city";
    private static final String CURRENT_CITY = "city";

    private DayForecast forecast = null;
    private City city = null;

    private String unitSpeed;
    private String unitPressure;
    private String unitTemperature;
    private String unitOrientation;
    private String unitHumidity;

    @InjectView(R.id.main)
    protected TextView mainTextView;
    @InjectView(R.id.temp)
    protected TextView tempTextView;
    @InjectView(R.id.description)
    protected TextView descriptionTextView;
    @InjectView(R.id.temp_min)
    protected TextView tempMinTextView;
    @InjectView(R.id.temp_max)
    protected TextView tempMaxTextView;
    @InjectView(R.id.pressure)
    protected TextView pressureTextView;
    @InjectView(R.id.sea_level)
    protected TextView seaLevelTextView;
    @InjectView(R.id.grd_level)
    protected TextView grndLevelTextView;
    @InjectView(R.id.humidity)
    protected TextView humidityTextView;
    @InjectView(R.id.all)
    protected TextView allTextView;
    @InjectView(R.id.wind_speed)
    protected TextView windSpeedTextView;
    @InjectView(R.id.deg)
    protected TextView windDegTextView;
    @InjectView(R.id.icon)
    protected ImageView iconImageView;

    public static DisplayDetailForecastFragment newInstance(DayForecast forecast, City city) {
        DisplayDetailForecastFragment fragmentDetail = new DisplayDetailForecastFragment();


        Bundle args = new Bundle();
        args.putParcelable(DisplayDetailForecastFragment.KEY_DAY_FORECAST, forecast);
        args.putParcelable(DisplayDetailForecastFragment.KEY_CITY, city);
        fragmentDetail.setArguments(args);

        return fragmentDetail;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Restore the ListView position
        if (savedInstanceState != null) {
            forecast = savedInstanceState.getParcelable(CURRENT_DETAIL);
            city = savedInstanceState.getParcelable(CURRENT_CITY);

        }
        //Inflate layout for the fragment
        View currentView = inflater.inflate(R.layout.fragment_forecast_detail, container, false);


        ButterKnife.inject(this, currentView);

        unitSpeed = getResources().getString(R.string.unit_speed);
        unitPressure = getResources().getString(R.string.unit_pressure);
        unitTemperature = getResources().getString(R.string.unit_temperature);
        unitOrientation = getResources().getString(R.string.unit_orientation);
        unitHumidity = getResources().getString(R.string.unit_humidity);


        if (getArguments() != null && getArguments().getParcelable(KEY_DAY_FORECAST) != null) { //we are in a new activity


            forecast = (DayForecast) getArguments().getParcelable(KEY_DAY_FORECAST); //we get the fortecast we want to display
            city = (City) getArguments().getParcelable(KEY_CITY); //we get the fortecast we want to display
        }


        if (forecast != null && city != null) {
            //Loading the image
            try {
                Properties prop = Tools.getProperties(getActivity(), "app.properties");
                String urlImages = prop.getProperty("app.forecast.imageurl");
                // Trigger the download of the URL asynchronously into the image view.
                Picasso.with(getActivity())
                        .load(urlImages + forecast.getWeatherForecast().get(0).getIcon() + ".png")
                        .placeholder(R.drawable.icon_weather)
                        .error(R.drawable.icon_weather)
                        .into(iconImageView);
            } catch (IOException e) {
                e.printStackTrace();
            }


            fillView(forecast);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                iconImageView.setTransitionName(DisplayForecastActivity.SHARED_FORECAST_ICON);
                mainTextView.setTransitionName(DisplayForecastActivity.SHARED_MAIN_TEXT);
                descriptionTextView.setTransitionName(DisplayForecastActivity.SHARED_DESC_TEXT);


            }

        } else {
            currentView.setVisibility(View.INVISIBLE);
        }


        return currentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.reset(this);
    }

    private void fillView(DayForecast forecast) {
        mainTextView.setText(forecast.getWeatherForecast().get(0).getMain());


        descriptionTextView.setText(forecast.getWeatherForecast().get(0).getDescription());

        //Main information

        tempTextView.setText(Float.toString(forecast.getMainForecast().getTemp()) + " " + unitTemperature);


        tempMinTextView.setText(Float.toString(forecast.getMainForecast().getTempMin()) + " " + unitTemperature);


        tempMaxTextView.setText(Float.toString(forecast.getMainForecast().getTempMax()) + " " + unitTemperature);


        pressureTextView.setText(Float.toString(forecast.getMainForecast().getPressure()) + " " + unitPressure);


        seaLevelTextView.setText(Float.toString(forecast.getMainForecast().getSeaLevel()) + " " + unitPressure);


        grndLevelTextView.setText(Float.toString(forecast.getMainForecast().getGrndLevel()) + " " + unitPressure);


        humidityTextView.setText(Integer.toString(forecast.getMainForecast().getHumidity()) + " " + unitHumidity);

        //Clouds & Wind


        humidityTextView.setText(Integer.toString(forecast.getMainForecast().getHumidity()));


        windSpeedTextView.setText(Float.toString(forecast.getWindForecast().getSpeed()) + " " + unitSpeed);


        windDegTextView.setText(Float.toString(forecast.getWindForecast().getDeg()) + " " + unitOrientation);
    }

    public void updateForecast(DayForecast forecast, City city) {

        View currentView = getView();
        this.forecast = forecast;
        this.city = city;


        //Loading the image
        try {
            Properties prop = Tools.getProperties(getActivity(), "app.properties");
            String urlImages = prop.getProperty("app.forecast.imageurl");
            // Trigger the download of the URL asynchronously into the image view.
            Picasso.with(getActivity())
                    .load(urlImages + forecast.getWeatherForecast().get(0).getIcon() + ".png")
                    .placeholder(R.drawable.icon_weather)
                    .error(R.drawable.icon_weather)
                    .into(iconImageView);
        } catch (IOException e) {
            e.printStackTrace();
        }


        fillView(forecast);
        currentView.setVisibility(View.VISIBLE);

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the ListView position
        outState.putParcelable(CURRENT_DETAIL, forecast);
        outState.putParcelable(CURRENT_CITY, city);

    }


    public void eraseDetail() {


        forecast = null;
        city = null;
        getView().setVisibility(View.INVISIBLE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_detail_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        Toast.makeText(getContext(),"click",Toast.LENGTH_LONG).show();
        switch (item.getItemId()) {
            case R.id.share_detail:


                if (forecast != null && city != null) {

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH'h'mm", Locale.FRANCE);

                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);

                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "MyWeather");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, "Hello! \nYour daily forecast : \ncity of  : " + city.getName()
                            + "\ndate : " + sdf.format(forecast.getDateForecast())
                            + "\nCurrent situation : " + forecast.getWeatherForecast().get(0).getDescription());
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                } else {
                    Toast.makeText(getActivity(), R.string.please_select_item, Toast.LENGTH_LONG).show();
                }
                break;
            case android.R.id.home:
                //called when the up affordance/carat in actionbar is pressed
                getActivity().onBackPressed();
                return true;
            default:
                break;


        }

        return true;
    }

}


