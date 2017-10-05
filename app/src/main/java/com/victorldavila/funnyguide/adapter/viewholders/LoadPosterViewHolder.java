package com.victorldavila.funnyguide.adapter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.victorldavila.funnyguide.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadPosterViewHolder extends RecyclerView.ViewHolder {

  public @BindView(R.id.realtive_layout_load) RelativeLayout relativeLayoutLoad;
  public @BindView(R.id.item_load_poster) ProgressBar itemLoadPoster;

  public LoadPosterViewHolder(View itemView) {
    super(itemView);

    ButterKnife.bind(this, itemView);
  }
}
