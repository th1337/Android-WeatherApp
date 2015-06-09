package com.worldline.fpl.myweather.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.worldline.fpl.myweather.R;
import com.worldline.fpl.myweather.model.DayForecast;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by a607937 on 09/06/2015.
 */
public class ForecastAdapter extends ArrayAdapter<DayForecast> {

    private LayoutInflater inflater;
    private SimpleDateFormat sdf;

    public ForecastAdapter(Context context, List<DayForecast> forecasts) {
        super(context, -1, forecasts);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView==null) {
            convertView = inflater.inflate(R.layout.item_forecast, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.dateTextView = (TextView) convertView.findViewById(R.id.date);
            viewHolder.mainTextView= (TextView) convertView.findViewById(R.id.main);
            viewHolder.descriptionTextView= (TextView) convertView.findViewById(R.id.description);
            viewHolder.temperatureTextView= (TextView) convertView.findViewById(R.id.temperature);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }




        //the current Forecast

        DayForecast currentForecast = getItem(position);


        //update the textViews with the forecast informations
        viewHolder.mainTextView.setText(currentForecast.getWeatherForecast().getMain());
        viewHolder.descriptionTextView.setText(currentForecast.getWeatherForecast().getDescription());

        //formating and displaying the date

        String dateForecast=sdf.format(currentForecast.getDateForecast());
        viewHolder.dateTextView.setText(dateForecast);


        viewHolder.temperatureTextView.setText( getContext().getString(R.string.format_temperature,
                currentForecast.getMainForecast().getTemp()));


        return convertView;
    }


    private static class ViewHolder {

        TextView dateTextView;
        TextView mainTextView;
        TextView descriptionTextView;
        TextView temperatureTextView;

    }

}
