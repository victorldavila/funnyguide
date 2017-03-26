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

public class MovieGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int FOOTER_SIZE = 1;

    public static final int FOOTER_TYPE = 10;
    public static final int ITEM_TYPE = 20;

    private ArrayList<Movie> items;

    private MovieFragmentView view;
    private boolean load;

    public MovieGridAdapter(MovieFragmentView view) {
        this.view = view;

        items = new ArrayList<>();
        load = true;
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
        if (holder instanceof PosterViewHolder) {
            PosterViewHolder posterViewHolder = (PosterViewHolder) holder;
            setInfoMovie(posterViewHolder, items.get(position));
        } else if (holder instanceof LoadPosterViewHolder) {
            LoadPosterViewHolder loadPosterViewHolder = (LoadPosterViewHolder) holder;
            if (isLoad())
                onEnableLoad(loadPosterViewHolder);
            else
                onDisableLoad(loadPosterViewHolder);
        }
    }

    private void setInfoMovie(PosterViewHolder posterViewHolder, Movie movie) {
        posterViewHolder.originalTitlePoster.setText(movie.getTitle());
        posterViewHolder.countVotePoster.setText(String.valueOf(movie.getVote_average()));
        posterViewHolder.yearReleasePoster.setText(movie.getRelease_date());
        posterViewHolder.imagePosterPoster.setController(FrescoHelper.loadImage(movie.getPoster_path(), posterViewHolder.loadImagePoster));
        posterViewHolder.imagePosterPoster.setOnClickListener(v -> view.changeActivity(movie, posterViewHolder.imagePosterPoster));
    }

    private void onEnableLoad(LoadPosterViewHolder loadPosterViewHolder) {
        loadPosterViewHolder.relativeLayoutLoad.setVisibility(View.VISIBLE);
    }

    private void onDisableLoad(LoadPosterViewHolder loadPosterViewHolder) {
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

    public boolean isLoad() {
        return load;
    }

    public void setLoad(boolean load) {
        this.load = load;
    }
}
