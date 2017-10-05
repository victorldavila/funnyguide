package com.victorldavila.funnyguide.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.victorldavila.funnyguide.FrescoHelper;
import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.adapter.viewholders.LoadPosterViewHolder;
import com.victorldavila.funnyguide.adapter.viewholders.PosterViewHolder;
import com.victorldavila.funnyguide.models.ResponseTv;
import com.victorldavila.funnyguide.view.fragments.TvFragmentView;

import java.util.ArrayList;
import java.util.List;

public class TvGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private static final int FOOTER_SIZE = 1;

  public static final int FOOTER_TYPE = 10;
  public static final int ITEM_TYPE = 20;

  private ArrayList<ResponseTv> items;
  private boolean load;

  private TvFragmentView view;

  public TvGridAdapter(TvFragmentView view) {
    this.view = view;

    items = new ArrayList<ResponseTv>();

    load = true;
  }

  public void addList(List<ResponseTv> items){
    this.items.addAll(items);
  }

  public void addItem(ResponseTv item){
    this.items.add(item);
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == TvGridAdapter.ITEM_TYPE) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster_layout, parent, false);
      return new PosterViewHolder(view);
    } else{
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster_load, parent, false);
      return new LoadPosterViewHolder(view);
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof PosterViewHolder) {
      PosterViewHolder posterViewHolder = (PosterViewHolder) holder;
      setInfoTv(posterViewHolder, items.get(position));
    } else if(holder instanceof LoadPosterViewHolder){
      LoadPosterViewHolder loadPosterViewHolder = (LoadPosterViewHolder) holder;
      if(isLoad())
        onEnableLoad(loadPosterViewHolder);
      else
        onDisableLoad(loadPosterViewHolder);
    }
  }

  private boolean isLoad() {
    return load;
  }

  public void setLoad(boolean load) {
    this.load = load;
  }

  @Override
  public int getItemCount() {
    return items.size() + FOOTER_SIZE;
  }

  @Override
  public int getItemViewType(int position) {
    if(position == (getItemCount() - 1))
      return TvGridAdapter.FOOTER_TYPE;
    else
      return TvGridAdapter.ITEM_TYPE;
  }

  public void setInfoTv(PosterViewHolder posterViewHolder, ResponseTv responseTv) {
    posterViewHolder.originalTitlePoster.setText(responseTv.getOriginal_name());
    posterViewHolder.countVotePoster.setText(String.valueOf(responseTv.getVote_average()));
    posterViewHolder.yearReleasePoster.setText(responseTv.getFirst_air_date());
    posterViewHolder.imagePosterPoster.setController(FrescoHelper.loadImage(responseTv.getPoster_path(), posterViewHolder.loadImagePoster));
    posterViewHolder.imagePosterPoster.setOnClickListener(v -> view.changeActivity(responseTv, posterViewHolder.imagePosterPoster));
  }


  public void onEnableLoad(LoadPosterViewHolder loadPosterViewHolder) {
    loadPosterViewHolder.relativeLayoutLoad.setVisibility(View.VISIBLE);
  }

  public void onDisableLoad(LoadPosterViewHolder loadPosterViewHolder) {
    loadPosterViewHolder.relativeLayoutLoad.setVisibility(View.GONE);
  }
}
