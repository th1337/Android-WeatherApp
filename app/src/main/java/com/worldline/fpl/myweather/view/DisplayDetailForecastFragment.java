package com.worldline.fpl.myweather.view;

;import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.worldline.fpl.myweather.R;
import com.worldline.fpl.myweather.model.DayForecast;

/**
 * Created by a607937 on 09/06/2015.
 */
public class DisplayDetailForecastFragment extends Fragment {

    private final static String KEY_DAY_FORECAST="DayForecast";//the key to get the current forecast in Bundle

    public static DisplayDetailForecastFragment newInstance(DayForecast forecast)
    {
        DisplayDetailForecastFragment fragmentDetail=new DisplayDetailForecastFragment();

        Bundle args = new Bundle();
        args.putParcelable(DisplayDetailForecastFragment.KEY_DAY_FORECAST, forecast);
        fragmentDetail.setArguments(args);

        return fragmentDetail;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Inflate layout for the fragment
        View currentView= inflater.inflate(R.layout.fragment_forecast_detail, container, false);



        final String UNIT_SPEED=getResources().getString(R.string.unit_speed);
        final String UNIT_PRESSURE=getResources().getString(R.string.unit_pressure);
        final String UNIT_TEMPERATURE=getResources().getString(R.string.unit_temperature);
        final String UNIT_ORIENTATION=getResources().getString(R.string.unit_orientation);
        final String UNIT_HUMIDITY=getResources().getString(R.string.unit_humidity);

        DayForecast forecast = (DayForecast) getArguments().getParcelable(KEY_DAY_FORECAST);//we get the fortecast we want to display



        //Header
        TextView mainTextView=(TextView)currentView.findViewById(R.id.main);//main
        mainTextView.setText(forecast.getWeatherForecast().getMain());

        TextView descriptionTextView=(TextView)currentView.findViewById(R.id.description);//description
        descriptionTextView.setText(forecast.getWeatherForecast().getDescription());

        //Main information

        TextView tempTextView=(TextView)currentView.findViewById(R.id.temp);//temperature
        tempTextView.setText(Float.toString(forecast.getMainForecast().getTemp())+" "+UNIT_TEMPERATURE);

        TextView tempMinTextView=(TextView)currentView.findViewById(R.id.temp_min);//temperature min
        tempMinTextView.setText(Float.toString(forecast.getMainForecast().getTemp_min())+" "+UNIT_TEMPERATURE);

        TextView tempMaxTextView=(TextView)currentView.findViewById(R.id.temp_max);//temperature max
        tempMaxTextView.setText(Float.toString(forecast.getMainForecast().getTemp_max())+" "+UNIT_TEMPERATURE);

        TextView pressureTextView=(TextView)currentView.findViewById(R.id.pressure);//pressure
        pressureTextView.setText(Float.toString(forecast.getMainForecast().getPressure())+" "+UNIT_PRESSURE);

        TextView sea_LevelTextView=(TextView)currentView.findViewById(R.id.sea_level);//sea level pressure
        sea_LevelTextView.setText(Float.toString(forecast.getMainForecast().getSea_level())+" "+UNIT_PRESSURE);

        TextView grnd_LevelTextView=(TextView)currentView.findViewById(R.id.grd_level);//grnd level pressure
        grnd_LevelTextView.setText(Float.toString(forecast.getMainForecast().getGrnd_level())+" "+UNIT_PRESSURE);

        TextView humidityTextView=(TextView)currentView.findViewById(R.id.humidity);//humidity
        humidityTextView.setText(Integer.toString(forecast.getMainForecast().getHumidity())+" "+UNIT_HUMIDITY);

        //Clouds & Wind

        TextView allTextView=(TextView)currentView.findViewById(R.id.all);//humidity
        humidityTextView.setText(forecast.getCloudForecast().getAll());

        TextView wind_SpeedTextView=(TextView)currentView.findViewById(R.id.wind_speed);//wind speed
        wind_SpeedTextView.setText(Float.toString(forecast.getWindForecast().getSpeed())+" "+UNIT_SPEED);

        TextView wind_DegTextView=(TextView)currentView.findViewById(R.id.deg);//wind speed
        wind_DegTextView.setText(Float.toString(forecast.getWindForecast().getDeg())+" "+UNIT_ORIENTATION);




        return  currentView;
    }



}
