package com.th1337.myweather.tools;


import android.content.Context;
import android.util.Log;


import com.th1337.myweather.model.Cloud;
import com.th1337.myweather.model.DayForecast;
import com.th1337.myweather.model.MainForecast;
import com.th1337.myweather.model.Weather;
import com.th1337.myweather.model.Wind;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by a607937 on 09/06/2015.
 */
public class Tools {

    /**
     * This method allows to get Properties file from Assets
     *
     * @param context  context to get Assets
     * @param filename the properties filename located in Assets
     * @return the Properties object associated to the file
     * @throws IOException
     */
    public static Properties getProperties(Context context, String filename) throws IOException

    {

        InputStream inputStream = context.getAssets().open("app.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        Log.d("forecast url", properties.getProperty("app.forecast.baseurl"));
        return properties;
    }

    /**
     * This method allows to parse a JSONList into a DayForecast List.
     *
     * @param list the json list to parse
     * @return a list of forecasts
     * @throws JSONException
     */
    public static List<DayForecast> parseForecastList(JSONArray list) throws JSONException, ParseException {

        List<DayForecast> result = new ArrayList<DayForecast>();
        for (int i = 0; i < list.length(); i++) {
            JSONObject currentForecast = list.getJSONObject(i);

            String dateTime = currentForecast.getString("dt_txt");
            Date dateForecast = convertStringToDate(dateTime);

            MainForecast mainForecast = parseMainForecast(currentForecast.getJSONObject("main")); //we get the mainForecast
            Weather weatherForecast = parseWeatherForecast(currentForecast.getJSONArray("weather").getJSONObject(0)); //we get the weather,
            //we only get the first of the list.
            Cloud cloudForecast = parseCloudForecast(currentForecast.getJSONObject("clouds")); //we get the clouds
            Wind windForecast = parseWindForecast(currentForecast.getJSONObject("wind"));
            Log.d("forecast json", "time " + dateTime);

            List<Weather> listWeather = new ArrayList<>();
            listWeather.add(weatherForecast);
            DayForecast currentDayForecast = new DayForecast(dateForecast, listWeather, mainForecast, cloudForecast, windForecast);
            result.add(currentDayForecast); //the we add the dayforecast to the result

        }


        return result;

    }


    /**
     * This method allows to parse a JSONObject into a Wind
     *
     * @param wind the JSONObject
     * @return
     */
    private static Wind parseWindForecast(JSONObject wind) throws JSONException {
        float speed = (float) wind.getDouble("speed");
        float deg = (float) wind.getDouble("deg");

        Wind result = new Wind(speed, deg);
        return result;
    }

    /**
     * This method allows to parse a JSONObject into a Cloud
     *
     * @param cloud the JSONObject
     * @return
     */
    private static Cloud parseCloudForecast(JSONObject cloud) throws JSONException {

        int all = cloud.getInt("all");

        Cloud result = new Cloud(all);
        return result;
    }

    /**
     * This method allows to parse a JSONObject into a Weather
     *
     * @param weather the JSONObject
     * @return
     */
    private static Weather parseWeatherForecast(JSONObject weather) throws JSONException {

        long id = weather.getLong("id");
        String main = weather.getString("main");
        String description = weather.getString("description");
        String icon = weather.getString("icon");


        Weather result = new Weather(id, main, description, icon);

        return result;
    }

    /**
     * This method allows to parse a JSONObject into a MainForecast
     *
     * @param main the JSONObject
     * @return
     */
    private static MainForecast parseMainForecast(JSONObject main) throws JSONException {

        float temp = (float) main.getDouble("temp");
        float tempMin = (float) main.getDouble("temp_min");
        float tempMax = (float) main.getDouble("temp_max");
        float pressure = (float) main.getDouble("pressure");
        float seaLevel = (float) main.getDouble("sea_level");
        float grndLevel = (float) main.getDouble("grnd_level");
        int humidity = main.getInt("humidity");
        float tempKf = 0;


        MainForecast result = new MainForecast(temp, tempMin, tempMax, pressure, seaLevel, grndLevel, humidity, tempKf);

        return result;
    }


    /**
     * Converts a string into a date
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date convertStringToDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date convertedDate = new Date();

        convertedDate = dateFormat.parse(date);


        return convertedDate;


    }

}
