package com.victorldavila.funnyguide.adapter;


import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.victorldavila.funnyguide.Util.FrescoUtil;
import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.adapter.viewholders.LoadPosterViewHolder;
import com.victorldavila.funnyguide.adapter.viewholders.PosterCardViewHolder;
import com.victorldavila.funnyguide.models.Season;

import java.util.ArrayList;
import java.util.List;

public class TvSeasonGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
  private static final int FOOTER_SIZE = 1;

  public static final int FOOTER_TYPE = 10;
  public static final int ITEM_TYPE = 20;

  private ArrayList<Season> items;

  private boolean load;

  public TvSeasonGridAdapter() {
    items = new ArrayList<Season>();

    load = true;
  }

  public void addList(List<Season> items){
    this.items.addAll(items);
  }

  public void addItem(Season item){
    this.items.add(item);
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == TvGridAdapter.ITEM_TYPE) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_poster_season, parent, false);
      return new PosterCardViewHolder(view);
    } else{
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster_load, parent, false);
      return new LoadPosterViewHolder(view);
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof PosterCardViewHolder) {
      PosterCardViewHolder posterCardViewHolder = (PosterCardViewHolder) holder;
      setInfoTv(posterCardViewHolder, items.get(position));
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

  public void setInfoTv(PosterCardViewHolder posterCardViewHolder, Season season) {
    if (TextUtils.isEmpty(season.getAir_date())) {
      posterCardViewHolder.posterReleaseDate.setVisibility(View.GONE);
    } else {
      posterCardViewHolder.posterReleaseDate.setText(season.getAir_date());
    }

    if (season.getEpisode_count() == 0) {
      posterCardViewHolder.posterCountEpisode.setVisibility(View.GONE);
    } else {
      posterCardViewHolder.posterCountEpisode.setText(String.valueOf(season.getEpisode_count()) + " Episodios");
    }

    if (season.getSeason_number() == 0) {
      posterCardViewHolder.posterCountSeason.setVisibility(View.GONE);
    } else {
      posterCardViewHolder.posterCountSeason.setText("Temporada " + String.valueOf(season.getSeason_number()));
    }

    posterCardViewHolder.imageSeasonPoster.setController(FrescoUtil.loadImage(season.getPoster_path(),
        posterCardViewHolder.posterLoadImage));
  }


  public void onEnableLoad(LoadPosterViewHolder loadPosterViewHolder) {
    loadPosterViewHolder.relativeLayoutLoad.setVisibility(View.VISIBLE);
  }

  public void onDisableLoad(LoadPosterViewHolder loadPosterViewHolder) {
    loadPosterViewHolder.relativeLayoutLoad.setVisibility(View.GONE);
  }
}
