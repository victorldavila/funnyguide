package com.victorldavila.funnyguide.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ResponseTv extends Item {
  private String name;
  private String original_name;
  private String first_air_date;
  private String last_air_date;
  private String homepage;
  private String type;
  private List<String> origin_country;
  private List<String> languages;

  private boolean in_production;

  private int number_of_episodes;
  private int number_of_seasons;
  private int[] episode_run_time;

  private List<Company> production_companies;
  private List<Season> seasons;
  private List<Genre> genres;
  private List<Network> networks;

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

  public List<String> getOrigin_country() {
        return origin_country;
    }

  public void setOrigin_country(List<String> origin_country) {
        this.origin_country = origin_country;
    }

  public String getLast_air_date() {
    return last_air_date;
  }

  public void setLast_air_date(String last_air_date) {
    this.last_air_date = last_air_date;
  }

  public String getHomepage() {
    return homepage;
  }

  public void setHomepage(String homepage) {
    this.homepage = homepage;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<String> getLanguages() {
    return languages;
  }

  public void setLanguages(List<String> languages) {
    this.languages = languages;
  }

  public boolean isIn_production() {
    return in_production;
  }

  public void setIn_production(boolean in_production) {
    this.in_production = in_production;
  }

  public int getNumber_of_episodes() {
    return number_of_episodes;
  }

  public void setNumber_of_episodes(int number_of_episodes) {
    this.number_of_episodes = number_of_episodes;
  }

  public int getNumber_of_seasons() {
    return number_of_seasons;
  }

  public void setNumber_of_seasons(int number_of_seasons) {
    this.number_of_seasons = number_of_seasons;
  }

  public int[] getEpisode_run_time() {
    return episode_run_time;
  }

  public void setEpisode_run_time(int[] episode_run_time) {
    this.episode_run_time = episode_run_time;
  }

  public List<Company> getProduction_companies() {
    return production_companies;
  }

  public void setProduction_companies(List<Company> production_companies) {
    this.production_companies = production_companies;
  }

  public List<Season> getSeasons() {
    return seasons;
  }

  public void setSeasons(List<Season> seasons) {
    this.seasons = seasons;
  }

  public List<Genre> getGenres() {
    return genres;
  }

  public void setGenres(List<Genre> genres) {
    this.genres = genres;
  }

  public List<Network> getNetworks() {
    return networks;
  }

  public void setNetworks(List<Network> networks) {
    this.networks = networks;
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
    dest.writeString(this.last_air_date);
    dest.writeString(this.homepage);
    dest.writeString(this.type);
    dest.writeStringList(this.origin_country);
    dest.writeStringList(this.languages);
    dest.writeByte(this.in_production ? (byte) 1 : (byte) 0);
    dest.writeInt(this.number_of_episodes);
    dest.writeInt(this.number_of_seasons);
    dest.writeIntArray(this.episode_run_time);
    dest.writeTypedList(this.production_companies);
    dest.writeTypedList(this.seasons);
    dest.writeTypedList(this.genres);
    dest.writeTypedList(this.networks);
  }

  public ResponseTv() {
  }

  protected ResponseTv(Parcel in) {
    super(in);
    this.name = in.readString();
    this.original_name = in.readString();
    this.first_air_date = in.readString();
    this.last_air_date = in.readString();
    this.homepage = in.readString();
    this.type = in.readString();
    this.origin_country = in.createStringArrayList();
    this.languages = in.createStringArrayList();
    this.in_production = in.readByte() != 0;
    this.number_of_episodes = in.readInt();
    this.number_of_seasons = in.readInt();
    this.episode_run_time = in.createIntArray();
    this.production_companies = in.createTypedArrayList(Company.CREATOR);
    this.seasons = in.createTypedArrayList(Season.CREATOR);
    this.genres = in.createTypedArrayList(Genre.CREATOR);
    this.networks = in.createTypedArrayList(Network.CREATOR);
  }

  public static final Creator<ResponseTv> CREATOR = new Creator<ResponseTv>() {
    @Override
    public ResponseTv createFromParcel(Parcel source) {
      return new ResponseTv(source);
    }

    @Override
    public ResponseTv[] newArray(int size) {
      return new ResponseTv[size];
    }
  };
}
