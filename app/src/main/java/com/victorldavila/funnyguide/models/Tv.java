package com.victorldavila.funnyguide.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by victor on 18/12/2016.
 */

public class Tv extends Item implements Parcelable{

    private String name;
    private String original_name;
    private String first_air_date;
    private String[] origin_country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String[] getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(String[] origin_country) {
        this.origin_country = origin_country;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.name);
        dest.writeString(this.original_name);
        dest.writeString(this.first_air_date);
        dest.writeStringArray(this.origin_country);
    }

    public Tv() {
    }

    protected Tv(Parcel in) {
        super(in);
        this.name = in.readString();
        this.original_name = in.readString();
        this.first_air_date = in.readString();
        this.origin_country = in.createStringArray();
    }

    public static final Creator<Tv> CREATOR = new Creator<Tv>() {
        @Override
        public Tv createFromParcel(Parcel source) {
            return new Tv(source);
        }

        @Override
        public Tv[] newArray(int size) {
            return new Tv[size];
        }
    };
}
