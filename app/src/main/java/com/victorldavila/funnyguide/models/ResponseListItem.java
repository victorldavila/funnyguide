package com.victorldavila.funnyguide.models;

import java.util.List;

public class ResponseListItem<T> {
  private int id;
  private int page;
  private List<T> results;
  private int total_pages;
  private int total_results;

  public int getId() {
        return id;
    }

  public void setId(int id) {
        this.id = id;
    }

  public int getPage() {
        return page;
    }

  public void setPage(int page) {
        this.page = page;
    }

  public List<T> getResults() {
        return results;
    }

  public void setResults(List<T> results) {
        this.results = results;
    }

  public int getTotal_pages() {
        return total_pages;
    }

  public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

  public int getTotal_results() {
        return total_results;
    }

  public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }
}
