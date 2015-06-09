package com.worldline.fpl.myweather.model;

import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by a607937 on 08/06/2015.
 */
public class Wind implements Serializable, Parcelable {


    private float speed;//the wind speed in m/s
    private float deg;// the orientation of the wind in degrees


    public Wind(float speed, float deg) {
        this.speed = speed;
        this.deg = deg;
    }


    //Parcelable
    public static final Parcelable.Creator<Wind> CREATOR = new Parcelable.Creator<Wind>()
    {
        @Override
        public Wind createFromParcel(Parcel source)
        {
            return new Wind(source);
        }

        @Override
        public Wind[] newArray(int size)
        {
            return new Wind[size];
        }
    };
    //Parcelable constructor
    private Wind(Parcel source) {
        this.speed=source.readFloat();
        this.deg=source.readFloat();
    }
    
    
    
    
    public float getDeg() {
        return deg;
    }

    public void setDeg(float deg) {
        this.deg = deg;
    }


    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(speed);
        dest.writeFloat(deg);
    }
}
