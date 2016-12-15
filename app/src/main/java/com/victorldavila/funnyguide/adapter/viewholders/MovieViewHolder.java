package com.victorldavila.funnyguide.adapter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.victorldavila.funnyguide.R;

/**
 * Created by victo on 13/12/2016.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder {

    public SimpleDraweeView imagePosterMovie;
    public TextView originalTitleMovie;
    public TextView countVoteMovie;
    public TextView yearReleaseMovie;

    public MovieViewHolder(View itemView) {
        super(itemView);

        imagePosterMovie = (SimpleDraweeView) itemView.findViewById(R.id.item_poster_img);
        originalTitleMovie = (TextView) itemView.findViewById(R.id.item_poster_name);
        countVoteMovie = (TextView) itemView.findViewById(R.id.item_vote_count);
        yearReleaseMovie = (TextView) itemView.findViewById(R.id.item_date_release);
    }
}
