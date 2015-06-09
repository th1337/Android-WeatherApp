package com.worldline.fpl.myweather.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.worldline.fpl.myweather.R;
import com.worldline.fpl.myweather.model.Cloud;
import com.worldline.fpl.myweather.model.DayForecast;
import com.worldline.fpl.myweather.model.MainForecast;
import com.worldline.fpl.myweather.model.Weather;
import com.worldline.fpl.myweather.model.Wind;
import com.worldline.fpl.myweather.service.ForecastLoadedInterface;
import com.worldline.fpl.myweather.service.WeatherService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by a607937 on 09/06/2015.
 */
public class DisplayForecastFragment extends Fragment implements AdapterView.OnItemClickListener, ForecastLoadedInterface {

    public static final String TAG = "DisplayForecastFragment";

    private List<DayForecast> forecasts;//the forecasts list

    private ListView listForecasts;//the view for the forecasts

    //a few examples
    static DayForecast f1=new DayForecast(new Date(), new Weather(800, "Clear", "sky is clear", "01d"),new MainForecast( 293.0f, 290.0f, 295.5f, 998, 1045.18f, 998, 70,0),new Cloud("0"), new Wind(1.96f,343.004f));
    static DayForecast f2=new DayForecast(new Date(), new Weather(801, "Clear", "sky is clear (not at all)", "01d"), new MainForecast( 293.0f, 290.0f, 295.5f, 998, 1045.18f, 998, 70,0),new Cloud("0"), new Wind(1.96f,343.004f));
    static DayForecast f3=new DayForecast(new Date(), new Weather(802, "Cloudy", "sky is covered", "01d"), new MainForecast( 293.0f, 290.0f, 295.5f, 998, 1045.18f, 998, 70,0), new Cloud("0"), new Wind(1.96f,343.004f));
    static DayForecast f4=new DayForecast(new Date(), new Weather(803, "Bad", "sky is awful ", "01d"), new MainForecast( 293.0f, 290.0f, 295.5f, 998, 1045.18f, 998, 70,0), new Cloud("0"), new Wind(1.96f,343.004f));


    OnHeadlineSelectedListener mCallback;

    public static DisplayForecastFragment newInstance() {
        return new DisplayForecastFragment();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mCallback != null) {
            mCallback.onForecastSelected(forecasts.get(position));
        }
    }

    @Override
    public void ForecastsLoaded(List<DayForecast> forecasts) {
        Log.d("forecast", "coming back");
    }

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        void onForecastSelected(DayForecast selectedForecast);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View currentView= inflater.inflate(R.layout.fragment_forecast_list, container, false);

        listForecasts=(ListView)currentView.findViewById(R.id.listViewForecasts);


        forecasts=new ArrayList<DayForecast>();
        //we fill the list with fake examples
        for(int i=0;i<30;i++) {
            forecasts.add(f1);
            forecasts.add(f2);
            forecasts.add(f3);
            forecasts.add(f4);
        }

        WeatherService weatherService=WeatherService.newInstance();

        try {
            weatherService.loadDayForecast(getActivity(),this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ForecastAdapter forecastAdapter=new ForecastAdapter(getActivity(),forecasts);
        listForecasts.setAdapter(forecastAdapter);
        listForecasts.setOnItemClickListener(this);


        return  currentView;
    }




}
