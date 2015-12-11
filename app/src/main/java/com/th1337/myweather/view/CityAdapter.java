package com.th1337.myweather.view;

/**
 * Created by a607937 on 11/06/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.th1337.myweather.model.City;
import com.th1337.myweather.R;


import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by a607937 on 09/06/2015.
 */
public class CityAdapter extends ArrayAdapter<City> {

    private LayoutInflater inflater;


    public CityAdapter(Context context, List<City> cities) {
        super(context, -1, cities);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_city, parent, false);

            viewHolder = new ViewHolder(convertView);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        //the current Forecast
        City currentCity = getItem(position);


        //update the textViews with the forecast informations
        viewHolder.nameTextView.setText(currentCity.getName());


        return convertView;
    }


    protected static class ViewHolder {

        @InjectView(R.id.city_name)
        protected TextView nameTextView;


        public ViewHolder(View convertView) {

            ButterKnife.inject(this, convertView);
        }
    }

}
