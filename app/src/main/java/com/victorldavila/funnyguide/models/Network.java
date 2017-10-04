package com.victorldavila.funnyguide.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Network implements Parcelable {
  private int id;
  private String name;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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
    dest.writeInt(this.id);
    dest.writeString(this.name);
  }

  public Network() {
  }

  protected Network(Parcel in) {
    this.id = in.readInt();
    this.name = in.readString();
  }

  public static final Parcelable.Creator<Network> CREATOR = new Parcelable.Creator<Network>() {
    @Override
    public Network createFromParcel(Parcel source) {
      return new Network(source);
    }

    @Override
    public Network[] newArray(int size) {
      return new Network[size];
    }
  };
}
