package com.victorldavila.funnyguide.adapter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.victorldavila.funnyguide.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PosterViewHolder extends RecyclerView.ViewHolder {

  public @BindView(R.id.item_poster_img) SimpleDraweeView imagePosterPoster;
  public @BindView(R.id.item_poster_name) TextView originalTitlePoster;
  public @BindView(R.id.item_vote_count) TextView countVotePoster;
  public @BindView(R.id.item_date_release) TextView yearReleasePoster;
  public @BindView(R.id.load_image_poster) ProgressBar loadImagePoster;
  public @BindView(R.id.layout_info_poster) RelativeLayout layoutInfoPoster;

  public PosterViewHolder(View itemView) {
    super(itemView);

    ButterKnife.bind(this, itemView);
  }
}
