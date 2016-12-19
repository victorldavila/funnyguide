package com.victorldavila.funnyguide.adapter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.victorldavila.funnyguide.R;

/**
 * Created by victo on 13/12/2016.
 */

public class PosterViewHolder extends RecyclerView.ViewHolder {

    public SimpleDraweeView imagePosterPoster;
    public TextView originalTitlePoster;
    public TextView countVotePoster;
    public TextView yearReleasePoster;
    public ProgressBar loadImagePoster;
    public RelativeLayout layoutInfoPoster;

    public PosterViewHolder(View itemView) {
        super(itemView);

        imagePosterPoster = (SimpleDraweeView) itemView.findViewById(R.id.item_poster_img);
        originalTitlePoster = (TextView) itemView.findViewById(R.id.item_poster_name);
        countVotePoster = (TextView) itemView.findViewById(R.id.item_vote_count);
        yearReleasePoster = (TextView) itemView.findViewById(R.id.item_date_release);
        loadImagePoster = (ProgressBar) itemView.findViewById(R.id.load_image_poster);
        layoutInfoPoster = (RelativeLayout) itemView.findViewById(R.id.layout_info_poster);
    }
}
