package com.worldline.fpl.myweather.view;

;import android.content.Intent;
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
import com.worldline.fpl.myweather.R;
import com.worldline.fpl.myweather.model.City;
import com.worldline.fpl.myweather.model.DayForecast;
import com.worldline.fpl.myweather.tools.Tools;

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

    private final static String KEY_DAY_FORECAST="DayForecast";//the key to get the current forecast in Bundle
    private static final String CURRENT_DETAIL ="forecast" ;
    private static final String KEY_CITY="city";
    public static final String TAG = "DisplayDetailForecastFragment";
    private static final String CURRENT_CITY = "city";

    private DayForecast forecast=null;
    private City city=null;

    String UNIT_SPEED;
    String UNIT_PRESSURE;
    String UNIT_TEMPERATURE;
    String UNIT_ORIENTATION;
    String UNIT_HUMIDITY;
    @InjectView(R.id.main) TextView mainTextView;
    @InjectView(R.id.temp) TextView tempTextView;
    @InjectView(R.id.description) TextView descriptionTextView;
    @InjectView(R.id.temp_min) TextView tempMinTextView;
    @InjectView(R.id.temp_max) TextView tempMaxTextView;
    @InjectView(R.id.pressure) TextView pressureTextView;
    @InjectView(R.id.sea_level) TextView sea_LevelTextView;
    @InjectView(R.id.grd_level) TextView grnd_LevelTextView;
    @InjectView(R.id.humidity) TextView humidityTextView;
    @InjectView(R.id.all) TextView allTextView;
    @InjectView(R.id.wind_speed) TextView wind_SpeedTextView;
    @InjectView(R.id.deg) TextView wind_DegTextView;
    @InjectView(R.id.icon)  ImageView iconImageView;
    public static DisplayDetailForecastFragment newInstance(DayForecast forecast, City city)
    {
        DisplayDetailForecastFragment fragmentDetail=new DisplayDetailForecastFragment();


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
        if(savedInstanceState!=null) {
            forecast = savedInstanceState.getParcelable(CURRENT_DETAIL);
            city=savedInstanceState.getParcelable(CURRENT_CITY);

        }
        //Inflate layout for the fragment
        View currentView= inflater.inflate(R.layout.fragment_forecast_detail, container, false);


        ButterKnife.inject(this, currentView);

        UNIT_SPEED=getResources().getString(R.string.unit_speed);
        UNIT_PRESSURE=getResources().getString(R.string.unit_pressure);
        UNIT_TEMPERATURE=getResources().getString(R.string.unit_temperature);
        UNIT_ORIENTATION=getResources().getString(R.string.unit_orientation);
        UNIT_HUMIDITY=getResources().getString(R.string.unit_humidity);






        if(getArguments()!=null && getArguments().getParcelable(KEY_DAY_FORECAST)!=null) { //we are in a new activity


            forecast = (DayForecast) getArguments().getParcelable(KEY_DAY_FORECAST);//we get the fortecast we want to display
            city = (City) getArguments().getParcelable(KEY_CITY);//we get the fortecast we want to display
        }



        if(forecast!=null && city!=null) {
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

        }
        else
        {
            currentView.setVisibility(View.INVISIBLE);
        }






        return  currentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.reset(this);
    }

    private void fillView(DayForecast forecast)
    {
        mainTextView.setText(forecast.getWeatherForecast().get(0).getMain());


        descriptionTextView.setText(forecast.getWeatherForecast().get(0).getDescription());

        //Main information


        tempTextView.setText(Float.toString(forecast.getMainForecast().getTemp()) + " " + UNIT_TEMPERATURE);


        tempMinTextView.setText(Float.toString(forecast.getMainForecast().getTemp_min()) + " " + UNIT_TEMPERATURE);


        tempMaxTextView.setText(Float.toString(forecast.getMainForecast().getTemp_max()) + " " + UNIT_TEMPERATURE);


        pressureTextView.setText(Float.toString(forecast.getMainForecast().getPressure()) + " " + UNIT_PRESSURE);


        sea_LevelTextView.setText(Float.toString(forecast.getMainForecast().getSea_level()) + " " + UNIT_PRESSURE);


        grnd_LevelTextView.setText(Float.toString(forecast.getMainForecast().getGrnd_level()) + " " + UNIT_PRESSURE);


        humidityTextView.setText(Integer.toString(forecast.getMainForecast().getHumidity()) + " " + UNIT_HUMIDITY);

        //Clouds & Wind


        humidityTextView.setText(Integer.toString(forecast.getCloudForecast().getAll()));


        wind_SpeedTextView.setText(Float.toString(forecast.getWindForecast().getSpeed()) + " " + UNIT_SPEED);


        wind_DegTextView.setText(Float.toString(forecast.getWindForecast().getDeg()) + " " + UNIT_ORIENTATION);
    }

    public void updateForecast(DayForecast forecast, City city) {

        View currentView=getView();
        this.forecast=forecast;
        this.city=city;


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

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the ListView position
        outState.putParcelable(CURRENT_DETAIL,forecast );
        outState.putParcelable(CURRENT_CITY, city);

    }


    public void eraseDetail() {


        forecast=null;
        city=null;
        getView().setVisibility(View.INVISIBLE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

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
        switch (item.getItemId()) {
            case R.id.share_detail :


                if(forecast!=null && city!=null) {

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH'h'mm", Locale.FRANCE);

                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);

                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "MyWeather");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, "Hello! \nYour daily forecast : \ncity of  : "+city.getName()+"\ndate : "+sdf.format(forecast.getDateForecast())+"\nCurrent situation : "+ forecast.getWeatherForecast().get(0).getDescription());
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                }else
                {
                    Toast.makeText(getActivity(),R.string.please_select_item,Toast.LENGTH_LONG).show();
                }
                break;
            case android.R.id.home:
                //called when the up affordance/carat in actionbar is pressed
                getActivity().onBackPressed();
            return true;


        }

        return true;
    }

}


