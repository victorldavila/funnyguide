package com.victorldavila.funnyguide.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Movie extends Item implements Parcelable{
  private boolean adult;
  private String release_date;
  private String original_title;
  private String title;
  private boolean video;

  public boolean isAdult() {
        return adult;
    }

  public void setAdult(boolean adult) {
        this.adult = adult;
    }

  public String getRelease_date() {
        return release_date;
    }

  public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

  public String getOriginal_title() {
        return original_title;
    }

  public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

  public String getTitle() {
        return title;
    }

  public void setTitle(String title) {
        this.title = title;
    }

  public boolean isVideo() {
        return video;
    }

  public void setVideo(boolean video) {
        this.video = video;
    }

  @Override
  public int describeContents() {
        return 0;
    }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
    dest.writeString(this.release_date);
    dest.writeString(this.original_title);
    dest.writeString(this.title);
    dest.writeByte(this.video ? (byte) 1 : (byte) 0);
  }

    public Movie() { }

  protected Movie(Parcel in) {
    super(in);
    this.adult = in.readByte() != 0;
    this.release_date = in.readString();
    this.original_title = in.readString();
    this.title = in.readString();
    this.video = in.readByte() != 0;
  }

  public static final Creator<Movie> CREATOR = new Creator<Movie>() {
    @Override
    public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

    @Override
    public Movie[] newArray(int size) {
            return new Movie[size];
        }
  };
}
