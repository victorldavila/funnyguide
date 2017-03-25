package com.victorldavila.funnyguide.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.adapter.viewholders.LoadPosterViewHolder;
import com.victorldavila.funnyguide.adapter.viewholders.PosterViewHolder;
import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.view.fragments.MovieFragmentView;
import com.victorldavila.funnyguide.view.presenters.MoviePresenterImp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 13/12/2016.
 */

public class MovieGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MoviePresenterImp.OnBindMovieGridAdapter {

    public static final int FOOTER_SIZE = 1;

    public static final int FOOTER_TYPE = 10;
    public static final int ITEM_TYPE = 20;

    private ArrayList<Movie> items;
    private boolean isLoad;

    private MoviePresenterImp presenter;
    private MovieFragmentView view;

    public MovieGridAdapter(MovieFragmentView view, MoviePresenterImp presenter) {
        this.view = view;
        this.presenter = presenter;

        items = new ArrayList<>();
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
        if(viewType == MovieGridAdapter.ITEM_TYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_layout, parent, false);
            return new PosterViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster_load, parent, false);
            return new LoadPosterViewHolder(view);
        }
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
        posterViewHolder.imagePosterPoster.setController(presenter.loadImage(movie.getPoster_path(), posterViewHolder.loadImagePoster));
        posterViewHolder.imagePosterPoster.setOnClickListener(v -> view.changeActivity(movie, posterViewHolder.imagePosterPoster));
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
        if(position == (getItemCount() - 1))
            return MovieGridAdapter.FOOTER_TYPE;
        else
            return MovieGridAdapter.ITEM_TYPE;
    }
}
