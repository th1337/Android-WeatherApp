package com.th1337.myweather.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by a607937 on 08/06/2015.
 */
public class Weather implements Serializable, Parcelable {

    //Parcelable
    public static final Parcelable.Creator<Weather> CREATOR = new Parcelable.Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel source) {
            return new Weather(source);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

    private long id; //the id of the weather
    private String main; //group of weather parameters (Rain, Snow, Extreme etc.)
    private String description; //weather condition within the group
    private String icon; //the id of the description icon


    //Parcelable constructor
    public Weather(Parcel source) {
        this.id = source.readLong();
        this.main = source.readString();
        this.description = source.readString();
        this.icon = source.readString();

    }

    public Weather(long id, String main, String description, String icon) {
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(id);
        dest.writeString(main);
        dest.writeString(description);
        dest.writeString(icon);

    }





    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


}
