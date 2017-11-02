package com.victorldavila.funnyguide.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.victorldavila.funnyguide.Util.DateUtil;
import com.victorldavila.funnyguide.Util.FrescoUtil;
import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.adapter.viewholders.LoadPosterViewHolder;
import com.victorldavila.funnyguide.adapter.viewholders.PosterViewHolder;
import com.victorldavila.funnyguide.models.ResponseMovie;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action2;

public class MovieGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  public static final int FOOTER_SIZE = 1;

  public static final int FOOTER_TYPE = 10;
  public static final int ITEM_TYPE = 20;

  private ArrayList<ResponseMovie> items;

  private Action2<ResponseMovie, SimpleDraweeView> mainAction = (responseMovie, simpleDraweeView) -> { };

  private boolean load;

  public MovieGridAdapter() {
    items = new ArrayList<>();

    load = true;
  }

  public void addList(List<ResponseMovie> items){
    this.items.addAll(items);
  }

  public void addItem(ResponseMovie item){
    this.items.add(item);
  }

  public void setMainAction(Action2<ResponseMovie, SimpleDraweeView> mainAction) {
    this.mainAction = mainAction;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if(viewType == MovieGridAdapter.ITEM_TYPE){
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster_layout, parent, false);
      return new PosterViewHolder(view);
    } else {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster_load, parent, false);
      return new LoadPosterViewHolder(view);
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof PosterViewHolder) {
      PosterViewHolder posterViewHolder = (PosterViewHolder) holder;
      setInfoMovie(posterViewHolder, items.get(position));
    } else if (holder instanceof LoadPosterViewHolder) {
      LoadPosterViewHolder loadPosterViewHolder = (LoadPosterViewHolder) holder;
      if (isLoad())
        onEnableLoad(loadPosterViewHolder);
      else
        onDisableLoad(loadPosterViewHolder);
    }
  }

  private void setInfoMovie(PosterViewHolder posterViewHolder, ResponseMovie responseMovie) {
    posterViewHolder.originalTitlePoster.setText(responseMovie.getTitle());
    posterViewHolder.countVotePoster.setText(String.valueOf(responseMovie.getVote_average()));
    posterViewHolder.yearReleasePoster.setText(DateUtil.getFormatDate(responseMovie.getRelease_date()));
    posterViewHolder.imagePosterPoster.setController(FrescoUtil.loadImage(responseMovie.getPoster_path(), posterViewHolder.loadImagePoster));
    posterViewHolder.imagePosterPoster.setOnClickListener(v -> mainAction.call(responseMovie, posterViewHolder.imagePosterPoster));
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
      return MovieGridAdapter.FOOTER_TYPE;
    else
      return MovieGridAdapter.ITEM_TYPE;
  }

  public boolean isLoad() {
    return load;
  }

  public void setLoad(boolean load) {
    this.load = load;
  }
}
