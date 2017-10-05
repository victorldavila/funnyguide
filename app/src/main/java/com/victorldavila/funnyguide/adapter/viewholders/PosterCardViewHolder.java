package com.victorldavila.funnyguide.adapter.viewholders;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.victorldavila.funnyguide.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PosterCardViewHolder extends RecyclerView.ViewHolder{
  public @BindView(R.id.item_poster_img) SimpleDraweeView imageSeasonPoster;
  public @BindView(R.id.item_poster_season) TextView posterCountSeason;
  public @BindView(R.id.item_count_episode) TextView posterCountEpisode;
  public @BindView(R.id.item_date_release) TextView posterReleaseDate;
  public @BindView(R.id.load_image_poster) ProgressBar posterLoadImage;

  public PosterCardViewHolder(View itemView) {
    super(itemView);

    ButterKnife.bind(this, itemView);
  }
}
