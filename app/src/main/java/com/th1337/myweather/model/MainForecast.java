package com.th1337.myweather.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Created by a607937 on 08/06/2015.
 */
public class MainForecast implements Serializable, Parcelable {

    //Parcelable
    public static final Parcelable.Creator<MainForecast> CREATOR = new Parcelable.Creator<MainForecast>() {
        @Override
        public MainForecast createFromParcel(Parcel source) {
            return new MainForecast(source);
        }

        @Override
        public MainForecast[] newArray(int size) {
            return new MainForecast[size];
        }
    };


    private float temp; //the current temperature in Kelvins

    @SerializedName("temp_min")
    private float tempMin; //the minimal temperature in Kelvins
    @SerializedName("temp_max")
    private float tempMax; //the maximal temperature in Kelvins
    private float pressure; //the current atmospheric pressure in  hPa
    @SerializedName("sea_level")
    private float seaLevel; //the atmospheric pressure on the sea level in hPa
    @SerializedName("grnd_level")
    private float grndLevel; //the atmospheric pressure on the ground level in hPa
    private int humidity; //the current humidity percentage
    @SerializedName("temp_kf")
    private float tempKf; //TODO meaning of this ?


    //Parcelable constructor
    public MainForecast(Parcel source) {
        this.temp = source.readFloat();
        this.tempMin = source.readFloat();
        this.tempMax = source.readFloat();
        this.pressure = source.readFloat();
        this.seaLevel = source.readFloat();
        this.grndLevel = source.readFloat();
        this.humidity = source.readInt();
        this.tempKf = source.readFloat();


    }

    public MainForecast(float temp, float tempMin, float tempMax, float pressure, float seaLevel, float grndLevel, int humidity, float tempKf) {
        this.temp = temp;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.pressure = pressure;
        this.seaLevel = seaLevel;
        this.grndLevel = grndLevel;
        this.humidity = humidity;
        this.tempKf = tempKf;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(temp);
        dest.writeFloat(tempMin);
        dest.writeFloat(tempMax);
        dest.writeFloat(pressure);
        dest.writeFloat(seaLevel);
        dest.writeFloat(grndLevel);
        dest.writeInt(humidity);
        dest.writeFloat(tempKf);
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

    public float getTempMin() {
        return tempMin;
    }

    public void setTempMin(float tempMin) {
        this.tempMin = tempMin;
    }

    public float getTempMax() {
        return tempMax;
    }

    public void setTempMax(float tempMax) {
        this.tempMax = tempMax;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(float seaLevel) {
        this.seaLevel = seaLevel;
    }

    public float getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(float grndLevel) {
        this.grndLevel = grndLevel;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public float getTempKf() {
        return tempKf;
    }

    public void setTempKf(float tempKf) {
        this.tempKf = tempKf;
    }


}


