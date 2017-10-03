package com.victorldavila.funnyguide.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ResponseMovie extends Item {
  private String homepage;
  private String imdb_id;
  private String release_date;
  private String original_title;
  private String title;
  private String tagline;
  //private String belongs_to_collection;

  private long budget;
  private long revenue;

  private int runtime;

  private List<Genre> genres;
  private List<Company> production_companies;
  private List<Country> production_countries;
  private List<Language> spoken_languages;

  private boolean video;
  private boolean adult;

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

  public String getHomepage() {
    return homepage;
  }

  public void setHomepage(String homepage) {
    this.homepage = homepage;
  }

  public String getImdb_id() {
    return imdb_id;
  }

  public void setImdb_id(String imdb_id) {
    this.imdb_id = imdb_id;
  }

  public String getTagline() {
    return tagline;
  }

  public void setTagline(String tagline) {
    this.tagline = tagline;
  }

  /*public String getBelongs_to_collection() {
    return belongs_to_collection;
  }

  public void setBelongs_to_collection(String belongs_to_collection) {
    this.belongs_to_collection = belongs_to_collection;
  }*/

  public long getBudget() {
    return budget;
  }

  public void setBudget(long budget) {
    this.budget = budget;
  }

  public long getRevenue() {
    return revenue;
  }

  public void setRevenue(long revenue) {
    this.revenue = revenue;
  }

  public int getRuntime() {
    return runtime;
  }

  public void setRuntime(int runtime) {
    this.runtime = runtime;
  }

  public List<Genre> getGenres() {
    return genres;
  }

  public void setGenres(List<Genre> genres) {
    this.genres = genres;
  }

  public List<Company> getProduction_companies() {
    return production_companies;
  }

  public void setProduction_companies(List<Company> production_companies) {
    this.production_companies = production_companies;
  }

  public List<Country> getProduction_countries() {
    return production_countries;
  }

  public void setProduction_countries(List<Country> production_countries) {
    this.production_countries = production_countries;
  }

  public List<Language> getSpoken_languages() {
    return spoken_languages;
  }

  public void setSpoken_languages(List<Language> spoken_languages) {
    this.spoken_languages = spoken_languages;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(this.homepage);
    dest.writeString(this.imdb_id);
    dest.writeString(this.release_date);
    dest.writeString(this.original_title);
    dest.writeString(this.title);
    dest.writeString(this.tagline);
    //dest.writeString(this.belongs_to_collection);
    dest.writeLong(this.budget);
    dest.writeLong(this.revenue);
    dest.writeInt(this.runtime);
    dest.writeTypedList(this.genres);
    dest.writeTypedList(this.production_companies);
    dest.writeTypedList(this.production_countries);
    dest.writeTypedList(this.spoken_languages);
    dest.writeByte(this.video ? (byte) 1 : (byte) 0);
    dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
  }

  public ResponseMovie() {
  }

  protected ResponseMovie(Parcel in) {
    super(in);
    this.homepage = in.readString();
    this.imdb_id = in.readString();
    this.release_date = in.readString();
    this.original_title = in.readString();
    this.title = in.readString();
    this.tagline = in.readString();
    //this.belongs_to_collection = in.readString();
    this.budget = in.readLong();
    this.revenue = in.readLong();
    this.runtime = in.readInt();
    this.genres = in.createTypedArrayList(Genre.CREATOR);
    this.production_companies = in.createTypedArrayList(Company.CREATOR);
    this.production_countries = in.createTypedArrayList(Country.CREATOR);
    this.spoken_languages = in.createTypedArrayList(Language.CREATOR);
    this.video = in.readByte() != 0;
    this.adult = in.readByte() != 0;
  }

  public static final Creator<ResponseMovie> CREATOR = new Creator<ResponseMovie>() {
    @Override
    public ResponseMovie createFromParcel(Parcel source) {
      return new ResponseMovie(source);
    }

    @Override
    public ResponseMovie[] newArray(int size) {
      return new ResponseMovie[size];
    }
  };
}
