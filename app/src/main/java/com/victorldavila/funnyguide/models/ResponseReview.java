package com.victorldavila.funnyguide.models;


import android.os.Parcel;
import android.os.Parcelable;

public class ResponseReview implements Parcelable {
  private String id;
  private String author;
  private String content;
  private String url;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.id);
    dest.writeString(this.author);
    dest.writeString(this.content);
    dest.writeString(this.url);
  }

  public ResponseReview() {
  }

  protected ResponseReview(Parcel in) {
    this.id = in.readString();
    this.author = in.readString();
    this.content = in.readString();
    this.url = in.readString();
  }

  public static final Parcelable.Creator<ResponseReview> CREATOR = new Parcelable.Creator<ResponseReview>() {
    @Override
    public ResponseReview createFromParcel(Parcel source) {
      return new ResponseReview(source);
    }

    @Override
    public ResponseReview[] newArray(int size) {
      return new ResponseReview[size];
    }
  };
}
