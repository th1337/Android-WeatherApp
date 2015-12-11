package com.th1337.myweather.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by a607937 on 08/06/2015.
 */
public class Cloud implements Serializable, Parcelable {



    //Parcelable
    public static final Parcelable.Creator<Cloud> CREATOR = new Parcelable.Creator<Cloud>() {
        @Override
        public Cloud createFromParcel(Parcel source) {
            return new Cloud(source);
        }

        @Override
        public Cloud[] newArray(int size) {
            return new Cloud[size];
        }
    };

    private int all; //cloudiness in %

    public Cloud(int all) {
        this.all = all;
    }

    //Parcelable constructor
    public Cloud(Parcel source) {
        this.all = source.readInt();


    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(all);
    }





}
