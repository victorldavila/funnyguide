package com.victorldavila.funnyguide.adapter.viewholders;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.victorldavila.funnyguide.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewViewHolder extends RecyclerView.ViewHolder {

  public @BindView(R.id.name_user_review) TextView nameUserReview;
  public @BindView(R.id.review_movie) TextView reviewMovie;

  public ReviewViewHolder(View itemView) {
    super(itemView);

    ButterKnife.bind(this, itemView);
  }
}

