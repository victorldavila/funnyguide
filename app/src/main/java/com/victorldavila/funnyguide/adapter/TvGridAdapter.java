package com.victorldavila.funnyguide.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.adapter.viewholders.LoadPosterViewHolder;
import com.victorldavila.funnyguide.adapter.viewholders.PosterViewHolder;
import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.models.Tv;
import com.victorldavila.funnyguide.view.presenters.TvPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victo on 19/12/2016.
 */

public class TvGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements TvPresenter.OnBindTvGridAdapter {
    private static final int FOOTER_SIZE = 1;

    public static final int FOOTER_TYPE = 10;
    public static final int ITEM_TYPE = 20;

    private ArrayList<Tv> items;
    private Context context;
    private boolean isLoad;

    private TvPresenter presenter;

    public TvGridAdapter(Context context, TvPresenter presenter) {
        this.context = context;
        this.presenter = presenter;

        items = new ArrayList<Tv>();
        isLoad = true;
    }

    public void addList(List<Tv> items){
        this.items.addAll(items);
    }

    public void addItem(Tv item){
        this.items.add(item);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return presenter.getViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        presenter.bindTvAdapter(holder, items, position, this);
    }

    @Override
    public int getItemCount() {
        return items.size() + FOOTER_SIZE;
    }

    @Override
    public int getItemViewType(int position) {
        return presenter.getItemViewType(position, getItemCount());
    }

    @Override
    public void setInfoTv(PosterViewHolder posterViewHolder, Tv tv) {
        posterViewHolder.originalTitlePoster.setText(presenter.getText(tv.getOriginal_name()));
        posterViewHolder.countVotePoster.setText(presenter.getText(String.valueOf(tv.getVote_average())));
        posterViewHolder.yearReleasePoster.setText(presenter.getText(tv.getFirst_air_date()));
        presenter.loadImage(posterViewHolder.imagePosterPoster, tv, posterViewHolder.loadImagePoster);
    }

    @Override
    public void onEnableLoad(LoadPosterViewHolder loadPosterViewHolder) {
        loadPosterViewHolder.relativeLayoutLoad.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDisableLoad(LoadPosterViewHolder loadPosterViewHolder) {
        loadPosterViewHolder.relativeLayoutLoad.setVisibility(View.GONE);
    }
}
