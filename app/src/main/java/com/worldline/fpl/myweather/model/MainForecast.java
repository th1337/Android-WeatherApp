package com.worldline.fpl.myweather.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


/**
 * Created by a607937 on 08/06/2015.
 */
public class MainForecast implements Serializable, Parcelable {

    private float temp;//the current temperature in Kelvins
    private float temp_min;//the minimal temperature in Kelvins
    private float temp_max;//the maximal temperature in Kelvins
    private float pressure;//the current atmospheric pressure in  hPa
    private float sea_level;//the atmospheric pressure on the sea level in hPa
    private float grnd_level;//the atmospheric pressure on the ground level in hPa
    private int humidity;//the current humidity percentage
    private float temp_kf;//TODO meaning of this ?

    //Parcelable
    public static final Parcelable.Creator<MainForecast> CREATOR = new Parcelable.Creator<MainForecast>()
    {
        @Override
        public MainForecast createFromParcel(Parcel source)
        {
            return new MainForecast(source);
        }

        @Override
        public MainForecast[] newArray(int size)
        {
            return new MainForecast[size];
        }
    };
    //Parcelable constructor
    public MainForecast(Parcel source) {
        this.temp=source.readFloat();
        this.temp_min=source.readFloat();
        this.temp_max=source.readFloat();
        this.pressure=source.readFloat();
        this.sea_level=source.readFloat();
        this.grnd_level=source.readFloat();
        this.humidity=source.readInt();
        this.temp_kf=source.readFloat();


    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(temp);
        dest.writeFloat(temp_min);
        dest.writeFloat(temp_max);
        dest.writeFloat(pressure);
        dest.writeFloat(sea_level);
        dest.writeFloat(grnd_level);
        dest.writeInt(humidity);
        dest.writeFloat(temp_kf);
    }


    public MainForecast(float temp, float temp_min, float temp_max, float pressure, float sea_level, float grnd_level, int humidity, float temp_kf) {
        this.temp = temp;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.pressure = pressure;
        this.sea_level = sea_level;
        this.grnd_level = grnd_level;
        this.humidity = humidity;
        this.temp_kf = temp_kf;
    }

    @Override
    public int describeContents() {
        return 0;
    }



    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(float temp_min) {
        this.temp_min = temp_min;
    }

    public float getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(float temp_max) {
        this.temp_max = temp_max;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getSea_level() {
        return sea_level;
    }

    public void setSea_level(float sea_level) {
        this.sea_level = sea_level;
    }

    public float getGrnd_level() {
        return grnd_level;
    }

    public void setGrnd_level(float grnd_level) {
        this.grnd_level = grnd_level;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public float getTemp_kf() {
        return temp_kf;
    }

    public void setTemp_kf(float temp_kf) {
        this.temp_kf = temp_kf;
    }


}


