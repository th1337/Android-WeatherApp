package com.th1337.myweather.view;

import android.app.SharedElementCallback;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.th1337.myweather.model.City;
import com.th1337.myweather.model.DayForecast;
import com.th1337.myweather.R;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by a607937 on 08/06/2015.
 */
public class DisplayForecastActivity extends BaseActivity implements DisplayForecastFragment.OnHeadlineSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    public static final String SHARED_FORECAST_ICON = "forecasticon";
    public static final String SHARED_MAIN_TEXT = "mainTextView";
    public static final String SHARED_DESC_TEXT = "descTextView";

    private static City lyonCity = new City(2996944l, "Lyon");
    private static City konstanzCity = new City(2885679l, "Konstanz");
    private static City moscowCity = new City(524901l, "Moscow");
    private static City xiaoCity = new City(1790100l, "Xiaolingwei");
    private static City narbonneCity = new City(2990919l, "Narbonne");
    private static City villeurbanneCity = new City(2968254l, "Villeurbanne");
    private static City leninCity = new City(536200l, "Leningradskaya");
    private static final String CURRENT_CITY = "current_city";

    private static Map<String,City> cities;

    private City currentCity = null;
    private FrameLayout detailContainer;


    private ActionBarDrawerToggle mDrawerToggle;

    private Toolbar toolbar;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //data for menu
        cities = new HashMap<>();
        cities.put(lyonCity.getName(),lyonCity);
        cities.put(moscowCity.getName(), moscowCity);
        cities.put(xiaoCity.getName(), xiaoCity);


        if (savedInstanceState != null) {
            currentCity = savedInstanceState.getParcelable(CURRENT_CITY); //we get back our city
        }


        if (currentCity == null) { //no savedInstanceState

            currentCity = lyonCity;
        }


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        ) {


            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                mDrawerToggle.syncState();
                getSupportActionBar().setTitle(currentCity.getName());
            }


            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mDrawerToggle.syncState();
                getSupportActionBar().setTitle(R.string.app_name);
            }
        };


        drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        for(Map.Entry<String,City> city : cities.entrySet()){
            navigationView.getMenu().add(city.getKey()).setIcon(R.drawable.ic_location_city_black_24dp);

        }


        DisplayForecastFragment listFrag = (DisplayForecastFragment) getSupportFragmentManager().findFragmentByTag(DisplayForecastFragment.TAG);

        if (listFrag == null) {
            listFrag = DisplayForecastFragment.newInstance(currentCity);

            // Add the fragment to the 'fragment_container' FrameLayout replace to avoid reinstancing overlaying fragments
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.list_frag, listFrag, DisplayForecastFragment.TAG)
                    .commit();

        }


        detailContainer = (FrameLayout) findViewById(R.id.detail_frag);
        if (detailContainer != null)//we are on a tablet
        {
            DisplayDetailForecastFragment detailFrag = (DisplayDetailForecastFragment) getSupportFragmentManager()
                    .findFragmentByTag(DisplayDetailForecastFragment.TAG);

            if (detailFrag == null) {


                detailFrag = DisplayDetailForecastFragment.newInstance(null, null);

                // Add the fragment to the 'fragment_container' FrameLayout replace to avoid reinstancing overlaying fragments
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.detail_frag, detailFrag, DisplayDetailForecastFragment.TAG)
                        .commit();
            }

        }


        getSupportActionBar().setTitle(currentCity.getName());



    }


    @Override
    public void onForecastSelected(DayForecast selectedForecast, View itemView) {

        DisplayDetailForecastFragment detailFrag = (DisplayDetailForecastFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_frag);


        if (detailContainer == null) {
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            mDrawerToggle.setHomeAsUpIndicator(getDrawerToggleDelegate().getThemeUpIndicator());
            setSupportActionBar(toolbar);

            if (detailFrag == null) {
                detailFrag = DisplayDetailForecastFragment.newInstance(selectedForecast, currentCity);
            }

            DisplayForecastFragment displayForecast = (DisplayForecastFragment) getSupportFragmentManager().findFragmentByTag(DisplayForecastFragment.TAG);

            // Add the fragment to the 'fragment_container' FrameLayout replace to avoid reinstancing overlaying fragments
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction()
                    //.setCustomAnimations(R.transition.fragment_detail_transition, R.transition.fragment_list_fadeout)
                    .replace(R.id.list_frag, detailFrag, DisplayDetailForecastFragment.TAG)
                    .addToBackStack(null);

            animateDetailsTransactionIfPossible(tx, displayForecast, itemView, detailFrag);

            tx.commit();

            //we go to next activity
            //  Intent intent = DisplayDetailForecastActivity.newIntent(this, selectedForecast,currentCity);

            //  startActivity(intent);
        } else {

            detailFrag.updateForecast(selectedForecast, currentCity);
        }
    }

    private void animateDetailsTransactionIfPossible(FragmentTransaction tx,
                                                     DisplayForecastFragment listFragment,
                                                     View view,
                                                     DisplayDetailForecastFragment detailFrag) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            TransitionInflater inflater = TransitionInflater.from(this);
            Transition transitionIn = inflater.inflateTransition(R.transition.fragment_detail_in);
            Transition transitionOut = inflater.inflateTransition(R.transition.fragment_detail_out);

            detailFrag.setSharedElementReturnTransition(inflater.inflateTransition(android.R.transition.move));


            Transition shared = TransitionInflater.from(this).inflateTransition(android.R.transition.move);
            shared.setDuration(500);


            detailFrag.setSharedElementEnterTransition(shared);


            detailFrag.setEnterTransition(transitionIn);
            detailFrag.setReturnTransition(transitionOut);
            //
            //  listFragment.setExitTransition(new Fade());
            detailFrag.setSharedElementReturnTransition(inflater.inflateTransition(android.R.transition.move));
            // listFragment.setReenterTransition(new Fade());

            View icon = view.findViewById(R.id.icon_miniature);
            View textMain = view.findViewById(R.id.main);
            View textDesc = view.findViewById(R.id.description);
            textMain.setTransitionName(SHARED_MAIN_TEXT);
            textDesc.setTransitionName(SHARED_DESC_TEXT);
            icon.setTransitionName(SHARED_FORECAST_ICON);
            tx.addSharedElement(icon, SHARED_FORECAST_ICON);
            tx.addSharedElement(textMain, SHARED_MAIN_TEXT);
            tx.addSharedElement(textDesc, SHARED_DESC_TEXT);
            this.setExitSharedElementCallback(new SharedElementCallback() {
                @Override
                public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                    super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
                }
            });
//            detailFrag.setExitTransition(transition);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // turn on the Navigation Drawer image;
        // this is called in the LowerLevelFragments
        mDrawerToggle.setDrawerIndicatorEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mDrawerToggle.isDrawerIndicatorEnabled()) {
                    return mDrawerToggle.onOptionsItemSelected(item);
                } else {
                    onBackPressed();
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);


        //saving the current city
        savedInstanceState.putParcelable(CURRENT_CITY, currentCity);


    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();

        //First we erase the detail view if necessary
        DisplayDetailForecastFragment detailFrag = (DisplayDetailForecastFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_frag);
        if (detailFrag != null) {

            detailFrag.eraseDetail();
        }

        //get the list fragment and display the new data
        DisplayForecastFragment listFrag = (DisplayForecastFragment) getSupportFragmentManager()
                .findFragmentById(R.id.list_frag);

        listFrag.eraseList();
        listFrag.setCity(cities.get(item.getTitle()));

        //update the currentCity
        currentCity = cities.get(item.getTitle());


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
