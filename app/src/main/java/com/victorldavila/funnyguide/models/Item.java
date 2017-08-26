package com.victorldavila.funnyguide.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
  private String poster_path;
  private String overview;
  private int[] genre_ids;
  private int id;
  private String original_language;
  private String backdrop_path;
  private double popularity;
  private int vote_count;
  private double vote_average;

  public String getPoster_path() {
        return poster_path;
    }

  public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

  public String getOverview() {
        return overview;
    }

  public void setOverview(String overview) {
        this.overview = overview;
    }

  public int[] getGenre_ids() {
        return genre_ids;
    }

  public void setGenre_ids(int[] genre_ids) {
        this.genre_ids = genre_ids;
    }

  public int getId() {
        return id;
    }

  public void setId(int id) {
        this.id = id;
    }

  public String getOriginal_language() {
        return original_language;
    }

  public void setOriginal_language(String original_language) {
    this.original_language = original_language;
  }

  public String getBackdrop_path() {
        return backdrop_path;
    }

  public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

  public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.poster_path);
      dest.writeString(this.overview);
      dest.writeIntArray(this.genre_ids);
      dest.writeInt(this.id);
      dest.writeString(this.original_language);
      dest.writeString(this.backdrop_path);
      dest.writeDouble(this.popularity);
      dest.writeInt(this.vote_count);
      dest.writeDouble(this.vote_average);
    }

  public Item() { }

  protected Item(Parcel in) {
    this.poster_path = in.readString();
    this.overview = in.readString();
    this.genre_ids = in.createIntArray();
    this.id = in.readInt();
    this.original_language = in.readString();
    this.backdrop_path = in.readString();
    this.popularity = in.readDouble();
    this.vote_count = in.readInt();
    this.vote_average = in.readDouble();
  }

  public static final Creator<Item> CREATOR = new Creator<Item>() {
    @Override
    public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

    @Override
    public Item[] newArray(int size) {
            return new Item[size];
        }
  };
}
