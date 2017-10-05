package com.victorldavila.funnyguide.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.adapter.viewholders.LoadPosterViewHolder;
import com.victorldavila.funnyguide.adapter.viewholders.ReviewViewHolder;
import com.victorldavila.funnyguide.models.ResponseReview;

import java.util.ArrayList;
import java.util.List;

public class ReviewMovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  public static final int FOOTER_SIZE = 1;

  public static final int FOOTER_TYPE = 10;
  public static final int ITEM_TYPE = 20;

  private List<ResponseReview> items;

  private boolean load;

  public ReviewMovieAdapter() {
    items = new ArrayList<>();

    load = true;
  }

  public void addList(List<ResponseReview> items){
    this.items.addAll(items);
  }

  public void addItem(ResponseReview item){
    this.items.add(item);
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if(viewType == MovieGridAdapter.ITEM_TYPE){
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review_layout, parent, false);
      return new ReviewViewHolder(view);
    } else {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster_load, parent, false);
      return new LoadPosterViewHolder(view);
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof ReviewViewHolder) {
      ReviewViewHolder posterViewHolder = (ReviewViewHolder) holder;
      setInfoReview(posterViewHolder, items.get(position));
    } else if (holder instanceof LoadPosterViewHolder) {
      LoadPosterViewHolder loadPosterViewHolder = (LoadPosterViewHolder) holder;
      if (isLoad())
        onEnableLoad(loadPosterViewHolder);
      else
        onDisableLoad(loadPosterViewHolder);
    }
  }

  private void setInfoReview(ReviewViewHolder reviewViewHolder, ResponseReview responseReview) {
    reviewViewHolder.nameUserReview.setText(responseReview.getAuthor());
    reviewViewHolder.reviewMovie.setText(responseReview.getContent());
  }

  private void onEnableLoad(LoadPosterViewHolder loadPosterViewHolder) {
    loadPosterViewHolder.relativeLayoutLoad.setVisibility(View.VISIBLE);
  }

  private void onDisableLoad(LoadPosterViewHolder loadPosterViewHolder) {
    loadPosterViewHolder.relativeLayoutLoad.setVisibility(View.GONE);
  }

  @Override
  public int getItemCount() {
    return items.size() + FOOTER_SIZE;
  }

  @Override
  public int getItemViewType(int position) {
    if(position == (getItemCount() - 1))
      return ReviewMovieAdapter.FOOTER_TYPE;
    else
      return ReviewMovieAdapter.ITEM_TYPE;
  }

  public boolean isLoad() {
    return load;
  }

  public void setLoad(boolean load) {
    this.load = load;
  }
}

