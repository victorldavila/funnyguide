package com.victorldavila.funnyguide.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Country implements Parcelable {
  private String iso_3166_1;
  private String name;

  public String getIso_3166_1() {
    return iso_3166_1;
  }

  public void setIso_3166_1(String iso_3166_1) {
    this.iso_3166_1 = iso_3166_1;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.iso_3166_1);
    dest.writeString(this.name);
  }

  public Country() {
  }

  protected Country(Parcel in) {
    this.iso_3166_1 = in.readString();
    this.name = in.readString();
  }

  public static final Parcelable.Creator<Country> CREATOR = new Parcelable.Creator<Country>() {
    @Override
    public Country createFromParcel(Parcel source) {
      return new Country(source);
    }

    @Override
    public Country[] newArray(int size) {
      return new Country[size];
    }
  };
}
