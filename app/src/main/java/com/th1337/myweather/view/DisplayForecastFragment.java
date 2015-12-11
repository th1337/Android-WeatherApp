package com.th1337.myweather.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.th1337.myweather.WeatherApplication;
import com.th1337.myweather.model.City;
import com.th1337.myweather.model.DayForecast;
import com.th1337.myweather.service.ForecastLoadedInterface;
import com.th1337.myweather.service.WeatherService;
import com.th1337.myweather.R;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by a607937 on 09/06/2015.
 */
public class DisplayForecastFragment extends BaseFragment implements AdapterView.OnItemClickListener,
        ForecastLoadedInterface, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = "DisplayForecastFragment";

    private static final int TODAY_FILTER = 1;
    private static final int TOMORROW_FILTER = 2;
    private static final int NO_FILTER = 0;
    private static final String CURRENT_FILTER = "filter";
    private static final String CURRENT_LIST_STATE = "list_state";
    private static final String CURRENT_CITY = "curentCity";

    private static final String[] CHOICES_FILTER = {"today", "tomorrow", "no filter"};

    /**
     * Stores the scroll position of the ListView
     */
    private static Parcelable listState = null;

    @Inject protected WeatherService weatherService;
    @Inject protected Properties properties;


    private int filter = 0;

    private City currentCity = null;


    private List<DayForecast> forecasts = new ArrayList<>(); //the forecasts list

    @InjectView(R.id.listViewForecasts) protected ListView listForecasts; //the view for the forecasts

    @InjectView(R.id.swipe_container_list) protected SwipeRefreshLayout swipeView; // the swipe to refresh view


    private ForecastAdapter forecastAdapter; //the adapter for the listView


    private OnHeadlineSelectedListener mCallback;


    public static DisplayForecastFragment newInstance(City city) {
        DisplayForecastFragment retour = new DisplayForecastFragment();
        retour.currentCity = city;
        return retour;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mCallback != null) {
            mCallback.onForecastSelected((DayForecast) parent.getItemAtPosition(position), view);
        }
    }

    @Override
    public void onForecastsLoaded(List<DayForecast> forecastsLoaded) {

        if (!isValid()) {
            return;
        }

        forecasts.clear();
        forecasts.addAll(forecastsLoaded);


        List<DayForecast> displayedForecasts = applyFilter(forecasts, filter);
        forecastAdapter = new ForecastAdapter(getActivity(), properties, displayedForecasts);
        listForecasts.setAdapter(forecastAdapter);


        if (listState != null) {
            listForecasts.onRestoreInstanceState(listState);
        }

        swipeView.setOnRefreshListener(this);
        swipeView.setRefreshing(false);

    }

    @Override
    public void onForecastLoadingError() {

        swipeView.setRefreshing(false);
    }

    @Override
    public void onRefresh() {


        if (currentCity != null) {

            swipeView.setOnRefreshListener(null);
            try {
                weatherService.loadDayForecast(this, currentCity.getId());

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            swipeView.setRefreshing(false);
        }
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ((WeatherApplication) getActivity().getApplication()).getAppComponent().inject(this);

        if (savedInstanceState != null) {
            currentCity = savedInstanceState.getParcelable(CURRENT_CITY);
            filter = savedInstanceState.getInt(CURRENT_FILTER);
            //  Restore the ListView position
            listState = savedInstanceState.getParcelable(CURRENT_LIST_STATE);

        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View currentView = inflater.inflate(R.layout.fragment_forecast_list, container, false);

        ButterKnife.inject(this, currentView);

        listForecasts.setOnItemClickListener(this);


        if (currentCity != null) {

            try {
                weatherService.loadDayForecast(this, currentCity.getId());

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


        return currentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (listForecasts != null)
        {
            listState = listForecasts.onSaveInstanceState();
        }
        // Save the ListView position
        outState.putParcelable(CURRENT_LIST_STATE, listState);

        outState.putParcelable(CURRENT_CITY, currentCity);
        outState.putInt(CURRENT_FILTER, filter);

    }


    public void setCity(City city) {

        currentCity = city;


        try {
            if (isValid()) {
                weatherService.loadDayForecast(this, currentCity.getId());
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    public void eraseList() {

        if (forecastAdapter != null) {
            forecastAdapter.clear();
            forecasts.clear();
        }
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.sort_item:

                //we get the current date and then get tomorrow


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.select_filter)

                        .setItems(CHOICES_FILTER, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                List<DayForecast> tmp = new ArrayList<>();

                                switch (which) {
                                    case 0: //today date
                                        filter = TODAY_FILTER;
                                        break;

                                    case 1: //tomorrow date
                                        filter = TOMORROW_FILTER;
                                        break;

                                    default:
                                        filter = NO_FILTER;
                                        break;


                                }


                                List<DayForecast> displayedForecasts = applyFilter(forecasts, filter);
                                forecastAdapter = new ForecastAdapter(getActivity(), properties, displayedForecasts);
                                listForecasts.setAdapter(forecastAdapter);
                                forecastAdapter.notifyDataSetChanged();

                            }
                        })
                        .create()
                        .show();


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private List<DayForecast> applyFilter(List<DayForecast> forecastsList, int filter) {

        List<DayForecast> result = new ArrayList<>();


        Date date = new Date();

        Calendar calendarCurrent = new GregorianCalendar();
        calendarCurrent.setTime(date);
        int year = calendarCurrent.get(Calendar.YEAR);
        int month = calendarCurrent.get(Calendar.MONTH);
        int day = calendarCurrent.get(Calendar.DAY_OF_MONTH);

        Calendar calendar = new GregorianCalendar();
        calendar.set(year, month, day, 23, 59, 59);

        calendar.add(Calendar.DAY_OF_YEAR, -1); //yesterday
        final Date dateYesterday = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1); //tomorrow
        final Date dateTomorrow = calendar.getTime();


        calendar.add(Calendar.DAY_OF_YEAR, 1); //after tomorrow
        final Date dateAfterTomorrow = calendar.getTime();

        switch (filter) {

            case TODAY_FILTER: //today date

                for (int i = 0; i < forecasts.size(); i++) {
                    if (forecasts.get(i).getDateForecast().before(dateTomorrow) && forecasts.get(i).getDateForecast().after(dateYesterday)) {
                        result.add(forecasts.get(i));
                    }
                }

                break;

            case TOMORROW_FILTER: //tomorrow date
                for (int i = 0; i < forecasts.size(); i++) {
                    if (forecasts.get(i).getDateForecast().after(dateTomorrow) && forecasts.get(i).getDateForecast().before(dateAfterTomorrow)) {
                        result.add(forecasts.get(i));
                    }
                }
                break;

            default:
                result.addAll(forecasts);
                break;


        }

        return result;


    }
    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        void onForecastSelected(DayForecast selectedForecast, View v);
    }

}
