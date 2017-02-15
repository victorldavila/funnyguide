package com.victorldavila.funnyguide.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.victorldavila.funnyguide.adapter.viewholders.LoadPosterViewHolder;
import com.victorldavila.funnyguide.adapter.viewholders.PosterViewHolder;
import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.view.presenters.MoviePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 13/12/2016.
 */

public class MovieGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MoviePresenter.OnBindMovieGridAdapter {

    public static final int FOOTER_SIZE = 1;

    public static final int FOOTER_TYPE = 10;
    public static final int ITEM_TYPE = 20;

    private ArrayList<Movie> items;
    private Context context;
    private boolean isLoad;

    private MoviePresenter presenter;

    public MovieGridAdapter(Context context, MoviePresenter presenter) {
        this.context = context;
        this.presenter = presenter;

        items = new ArrayList<Movie>();
        isLoad = true;
    }

    public void addList(List<Movie> items){
        this.items.addAll(items);
    }

    public void addItem(Movie item){
        this.items.add(item);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return presenter.getViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        presenter.bindMovieAdapter(holder, items, position, this);
    }

    @Override
    public void setInfoMovie(PosterViewHolder posterViewHolder, Movie movie) {
        posterViewHolder.originalTitlePoster.setText(presenter.getText(movie.getTitle()));
        posterViewHolder.countVotePoster.setText(presenter.getText(String.valueOf(movie.getVote_average())));
        posterViewHolder.yearReleasePoster.setText(presenter.getText(movie.getRelease_date()));
        presenter.loadImage(posterViewHolder.imagePosterPoster, movie, posterViewHolder.loadImagePoster);
        posterViewHolder.imagePosterPoster.setOnClickListener(v -> presenter.clickPoster(context,posterViewHolder.imagePosterPoster, movie));
    }

    @Override
    public void onEnableLoad(LoadPosterViewHolder loadPosterViewHolder) {
        loadPosterViewHolder.relativeLayoutLoad.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDisableLoad(LoadPosterViewHolder loadPosterViewHolder) {
        loadPosterViewHolder.relativeLayoutLoad.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return items.size() + FOOTER_SIZE;
    }

    @Override
    public int getItemViewType(int position) {
        return presenter.getItemViewType(position, getItemCount());
    }
}
