package com.worldline.fpl.myweather.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.worldline.fpl.myweather.R;
import com.worldline.fpl.myweather.model.DayForecast;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by a607937 on 09/06/2015.
 */
public class ForecastAdapter extends ArrayAdapter<DayForecast> {

    private LayoutInflater inflater;
    private SimpleDateFormat sdf;


    String urlImages;

    public ForecastAdapter(Context context,Properties properties, List<DayForecast> forecasts) {
        super(context, -1, forecasts);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sdf = new SimpleDateFormat("dd/MM/yyyy HH'h'mm", Locale.FRANCE);


        urlImages=properties.getProperty("app.forecast.imageurl");


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView==null) {
            convertView = inflater.inflate(R.layout.item_forecast, parent, false);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }




        //the current Forecast
        DayForecast currentForecast = getItem(position);
        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(getContext())
                .load(urlImages+currentForecast.getWeatherForecast().get(0).getIcon()+".png")
                .placeholder(R.drawable.icon_weather)
                .error(R.drawable.icon_weather)
                .into(viewHolder.iconImageView);






        //update the textViews with the forecast informations
        viewHolder.mainTextView.setText(currentForecast.getWeatherForecast().get(0).getMain());
        viewHolder.descriptionTextView.setText(currentForecast.getWeatherForecast().get(0).getDescription());

        //formating and displaying the date

        String dateForecast=sdf.format(currentForecast.getDateForecast());
        viewHolder.dateTextView.setText(dateForecast);


        viewHolder.temperatureTextView.setText( getContext().getString(R.string.format_temperature,
                currentForecast.getMainForecast().getTemp()));




        return convertView;
    }


    protected static class ViewHolder {

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }


        @InjectView(R.id.date)TextView dateTextView;
        @InjectView(R.id.main)TextView mainTextView;
        @InjectView(R.id.description)TextView descriptionTextView;
        @InjectView(R.id.temperature)TextView temperatureTextView;
        @InjectView(R.id.icon_miniature)ImageView iconImageView;

    }

}
