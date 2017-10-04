package com.victorldavila.funnyguide.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Season implements Parcelable {
  private int id;
  private int episode_count;
  private int season_number;

  private String air_date;
  private String poster_path;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getEpisode_count() {
    return episode_count;
  }

  public void setEpisode_count(int episode_count) {
    this.episode_count = episode_count;
  }

  public int getSeason_number() {
    return season_number;
  }

  public void setSeason_number(int season_number) {
    this.season_number = season_number;
  }

  public String getAir_date() {
    return air_date;
  }

  public void setAir_date(String air_date) {
    this.air_date = air_date;
  }

  public String getPoster_path() {
    return poster_path;
  }

  public void setPoster_path(String poster_path) {
    this.poster_path = poster_path;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.id);
    dest.writeInt(this.episode_count);
    dest.writeInt(this.season_number);
    dest.writeString(this.air_date);
    dest.writeString(this.poster_path);
  }

  public Season() {
  }

  protected Season(Parcel in) {
    this.id = in.readInt();
    this.episode_count = in.readInt();
    this.season_number = in.readInt();
    this.air_date = in.readString();
    this.poster_path = in.readString();
  }

  public static final Parcelable.Creator<Season> CREATOR = new Parcelable.Creator<Season>() {
    @Override
    public Season createFromParcel(Parcel source) {
      return new Season(source);
    }

    @Override
    public Season[] newArray(int size) {
      return new Season[size];
    }
  };
}
