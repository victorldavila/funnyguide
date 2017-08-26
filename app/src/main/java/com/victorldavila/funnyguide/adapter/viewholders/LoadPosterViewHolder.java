package com.victorldavila.funnyguide.adapter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.victorldavila.funnyguide.R;

public class LoadPosterViewHolder extends RecyclerView.ViewHolder {

  public RelativeLayout relativeLayoutLoad;
  public ProgressBar itemLoadPoster;

  public LoadPosterViewHolder(View itemView) {
    super(itemView);

    relativeLayoutLoad = (RelativeLayout) itemView.findViewById(R.id.realtive_layout_load);
    itemLoadPoster = (ProgressBar) itemView.findViewById(R.id.item_load_poster);
  }
}
