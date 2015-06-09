package com.worldline.fpl.myweather.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * This class represents the Forecast for one day
 * Created by a607937 on 08/06/2015.
 */
public class DayForecast implements Parcelable{

    private Date dateForecast; //the forecast's date

    private Weather weatherForecast;//the weather for the current Forecast

    private MainForecast mainForecast;//the main information of this forecast

    private Cloud cloudForecast;//the clouds information for the day

    private Wind windForecast;//the wind information for the day


    //Parcelable
    public static final Parcelable.Creator<DayForecast> CREATOR = new Parcelable.Creator<DayForecast>()
    {
        @Override
        public DayForecast createFromParcel(Parcel source)
        {
            return new DayForecast(source);
        }

        @Override
        public DayForecast[] newArray(int size)
        {
            return new DayForecast[size];
        }
    };
    //Parcelable constructor
    public DayForecast(Parcel source) {

        this.dateForecast=new Date(source.readLong());
        this.weatherForecast=source.readParcelable(getClass().getClassLoader());
        this.mainForecast=source.readParcelable(getClass().getClassLoader());
        this.cloudForecast=source.readParcelable(getClass().getClassLoader());
        this.windForecast=source.readParcelable(getClass().getClassLoader());

    }








    public DayForecast(Date dateForecast, Weather weatherForecast, MainForecast mainForecast, Cloud cloudForecast, Wind windForecast) {
        this.dateForecast = dateForecast;
        this.weatherForecast = weatherForecast;
        this.mainForecast = mainForecast;
        this.cloudForecast = cloudForecast;
        this.windForecast = windForecast;
    }



    public Date getDateForecast() {
        return dateForecast;
    }

    public void setDateForecast(Date dateForecast) {
        this.dateForecast = dateForecast;
    }

    public Weather getWeatherForecast() {
        return weatherForecast;
    }

    public void setWeatherForecast(Weather weatherForecast) {
        this.weatherForecast = weatherForecast;
    }

    public MainForecast getMainForecast() {
        return mainForecast;
    }

    public void setMainForecast(MainForecast mainForecast) {
        this.mainForecast = mainForecast;
    }

    public Cloud getCloudForecast() {
        return cloudForecast;
    }

    public void setCloudForecast(Cloud cloudForecast) {
        this.cloudForecast = cloudForecast;
    }

    public Wind getWindForecast() {
        return windForecast;
    }

    public void setWindForecast(Wind windForecast) {
        this.windForecast = windForecast;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(dateForecast.getTime());

        dest.writeParcelable(weatherForecast,0);
        dest.writeParcelable(mainForecast,0);
        dest.writeParcelable(cloudForecast,0);
        dest.writeParcelable(windForecast,0);
    }
};


