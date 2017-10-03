package com.victorldavila.funnyguide.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Language implements Parcelable {
  private String iso_639_1;
  private String name;

  public String getIso_639_1() {
    return iso_639_1;
  }

  public void setIso_639_1(String iso_639_1) {
    this.iso_639_1 = iso_639_1;
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
    dest.writeString(this.iso_639_1);
    dest.writeString(this.name);
  }

  public Language() {
  }

  protected Language(Parcel in) {
    this.iso_639_1 = in.readString();
    this.name = in.readString();
  }

  public static final Parcelable.Creator<Language> CREATOR = new Parcelable.Creator<Language>() {
    @Override
    public Language createFromParcel(Parcel source) {
      return new Language(source);
    }

    @Override
    public Language[] newArray(int size) {
      return new Language[size];
    }
  };
}
