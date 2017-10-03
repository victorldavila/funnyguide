package com.victorldavila.funnyguide.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Company implements Parcelable {
  private int id;
  private String name;

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.id);
    dest.writeString(this.name);
  }

  public Company() {
  }

  protected Company(Parcel in) {
    this.id = in.readInt();
    this.name = in.readString();
  }

  public static final Parcelable.Creator<Company> CREATOR = new Parcelable.Creator<Company>() {
    @Override
    public Company createFromParcel(Parcel source) {
      return new Company(source);
    }

    @Override
    public Company[] newArray(int size) {
      return new Company[size];
    }
  };
}
