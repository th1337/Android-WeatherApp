package com.worldline.fpl.myweather.view;

import android.app.SharedElementCallback;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.worldline.fpl.myweather.R;
import com.worldline.fpl.myweather.model.City;
import com.worldline.fpl.myweather.model.DayForecast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a607937 on 08/06/2015.
 */
public class DisplayForecastActivity extends BaseActivity implements DisplayForecastFragment.OnHeadlineSelectedListener, AdapterView.OnItemClickListener {

    public static final String SHARED_FORECAST_ICON = "forecasticon";
    public static final String SHARED_MAIN_TEXT ="mainTextView" ;
    public static final String SHARED_DESC_TEXT ="descTextView" ;


    private static List<City> cities;

    private City currentCity=null;
    private FrameLayout detailContainer;

    private DrawerLayout citiesDrawer;

    private ActionBarDrawerToggle mDrawerToggle;


    private ListView drawerList;
    private static City lyonCity=new City(2996944l,"Lyon");
    private static City konstanzCity=new City(2885679l,"Konstanz");
    private static City moscowCity=new City(524901l,"Moscow");
    private static City xiaoCity=new City(1790100l,"Xiaolingwei");
    private static City narbonneCity=new City(2990919l,"Narbonne");
    private static City villeurbanneCity=new City(2968254l,"Villeurbanne");
    private static City leninCity=new City(536200l,"Leningradskaya");


    private static final String CURRENT_CITY = "current_city";



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_list);





        //fake data
        cities=new ArrayList<>();
        cities.add(lyonCity);
        cities.add(konstanzCity);
        cities.add(moscowCity);
        cities.add(xiaoCity);
        cities.add(narbonneCity);
        cities.add(villeurbanneCity);
        cities.add(leninCity);

        cities.add(lyonCity);
        cities.add(konstanzCity);
        cities.add(moscowCity);
        cities.add(xiaoCity);
        cities.add(narbonneCity);
        cities.add(villeurbanneCity);
        cities.add(leninCity);


        if(savedInstanceState!=null)
        {
            currentCity=savedInstanceState.getParcelable(CURRENT_CITY);//we get back our city
        }

        citiesDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        drawerList.setAdapter(new CityAdapter(this, cities));
        // Set the list's click listener
        drawerList.setOnItemClickListener(this);



        if(currentCity==null) {//no savedInstanceState

            currentCity = cities.get(0);
        }



        DisplayForecastFragment listFrag = (DisplayForecastFragment) getSupportFragmentManager().findFragmentByTag(DisplayForecastFragment.TAG);

        if (listFrag == null) {
            listFrag = DisplayForecastFragment.newInstance(currentCity);

            // Add the fragment to the 'fragment_container' FrameLayout replace to avoid reinstancing overlaying fragments
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.list_frag, listFrag, DisplayForecastFragment.TAG)
                    .commit();

        }





        detailContainer=(FrameLayout) findViewById(R.id.detail_frag);
        if(detailContainer != null)//we are on a tablet
        {
            DisplayDetailForecastFragment detailFrag = (DisplayDetailForecastFragment) getSupportFragmentManager().findFragmentByTag(DisplayDetailForecastFragment.TAG);

            if (detailFrag == null) {
                detailFrag = DisplayDetailForecastFragment.newInstance(null, null);

                // Add the fragment to the 'fragment_container' FrameLayout replace to avoid reinstancing overlaying fragments
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.detail_frag, detailFrag, DisplayDetailForecastFragment.TAG)
                        .commit();
            }

        }



        getSupportActionBar().setTitle(currentCity.getName());




        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                citiesDrawer,         /* DrawerLayout object */

                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                mDrawerToggle.syncState();
                getSupportActionBar().setTitle(currentCity.getName());
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mDrawerToggle.syncState();
                getSupportActionBar().setTitle(R.string.app_name);
            }
        };

        mDrawerToggle.syncState();
        // Set the drawer toggle as the DrawerListener
        citiesDrawer.setDrawerListener(mDrawerToggle);


        //managing actionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        // However, if we're being restored from a previous state,
        // then we don't need to do anything and should return or else
        // we could end up with overlapping fragments.
//        if (savedInstanceState != null) {
//            return;
//        }

       // DisplayForecastFragment listFragment = (DisplayForecastFragment) getSupportFragmentManager().findFragmentByTag(DisplayForecastFragment.TAG);

       /** if (listFragment == null) {
            listFragment = DisplayForecastFragment.newInstance();
        }

        // Add the fragment to the 'fragment_container' FrameLayout replace to avoid reinstancing overlaying fragments
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, listFragment, DisplayForecastFragment.TAG)
                .commit();
        */


    }


    @Override
    public void onForecastSelected(DayForecast selectedForecast, View itemView) {

        DisplayDetailForecastFragment detailFrag = (DisplayDetailForecastFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_frag);


        if (detailContainer == null) {
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            if (detailFrag == null) {
                detailFrag = DisplayDetailForecastFragment.newInstance(selectedForecast, currentCity);
            }

            DisplayForecastFragment displayForecast = (DisplayForecastFragment) getSupportFragmentManager().findFragmentByTag(DisplayForecastFragment.TAG);

            // Add the fragment to the 'fragment_container' FrameLayout replace to avoid reinstancing overlaying fragments
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction()
//                    .setCustomAnimations(R.transition.fragment_detail_transition, R.transition.fragment_list_fadeout)
                    .replace(R.id.list_frag, detailFrag, DisplayDetailForecastFragment.TAG)
                    .addToBackStack(null);

            animateDetailsTransactionIfPossible(tx, displayForecast, itemView, detailFrag);

            tx.commit();

           //we go to next activity
          //  Intent intent = DisplayDetailForecastActivity.newIntent(this, selectedForecast,currentCity);

          //  startActivity(intent);
        } else {

            detailFrag.updateForecast(selectedForecast,currentCity);
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
            View textMain= view.findViewById(R.id.main);
            View textDesc = view.findViewById(R.id.description);
            textMain.setTransitionName(SHARED_MAIN_TEXT);
            textDesc.setTransitionName(SHARED_DESC_TEXT);
            icon.setTransitionName(SHARED_FORECAST_ICON);
            tx.addSharedElement(icon, SHARED_FORECAST_ICON);
            tx.addSharedElement(textMain,SHARED_MAIN_TEXT);
            tx.addSharedElement(textDesc,SHARED_DESC_TEXT);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //First we erase the detail view if necessary
        DisplayDetailForecastFragment detailFrag = (DisplayDetailForecastFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_frag);
        if (detailFrag != null){

            detailFrag.eraseDetail();
        }

        //get the list fragment and display the new data
        DisplayForecastFragment listFrag = (DisplayForecastFragment) getSupportFragmentManager()
                .findFragmentById(R.id.list_frag);

        listFrag.eraseList();
        listFrag.setCity(cities.get(position));

        //update the currentCity
        currentCity=cities.get(position);


        Toast.makeText(this,"selected " + cities.get(position).getName(),Toast.LENGTH_LONG).show();

        //then we close the drawer
        drawerList.setItemChecked(position, true);
        citiesDrawer.closeDrawer(drawerList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);


        //saving the current city
        savedInstanceState.putParcelable(CURRENT_CITY, currentCity);


    }



}
