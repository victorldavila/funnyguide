package com.victorldavila.funnyguide.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.victorldavila.funnyguide.R;
import com.victorldavila.funnyguide.adapter.viewholders.LoadPosterViewHolder;
import com.victorldavila.funnyguide.adapter.viewholders.MovieViewHolder;
import com.victorldavila.funnyguide.models.Movie;
import com.victorldavila.funnyguide.view.presenters.MoviePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victo on 13/12/2016.
 */

public class MovieGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int FOOTER_SIZE = 1;

    private static final int FOOTER_TYPE = 10;
    private static final int ITEM_TYPE = 20;

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

        if(viewType == ITEM_TYPE){
            View view = ViewGroup.inflate(context, R.layout.item_movie_layout, parent);
            return new MovieViewHolder(view);
        } else{
            View view = ViewGroup.inflate(context, R.layout.item_poster_load, parent);
            return new LoadPosterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MovieViewHolder){
            MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
            setInfoMovie(movieViewHolder, items.get(position));
        } else if(holder instanceof LoadPosterViewHolder){
            LoadPosterViewHolder loadPosterViewHolder = (LoadPosterViewHolder) holder;
            setLoadVisibility(loadPosterViewHolder);
        }
    }

    private void setInfoMovie(MovieViewHolder movieViewHolder, Movie movie) {
        movieViewHolder.originalTitleMovie.setText(presenter.getText(movie.getOriginal_title()));
        movieViewHolder.countVoteMovie.setText(presenter.getText(String.valueOf(movie.getVote_count())));
        movieViewHolder.yearReleaseMovie.setText(presenter.getText(movie.getRelease_date()));
    }

    private void setLoadVisibility(LoadPosterViewHolder loadPosterViewHolder) {
        if(presenter.isLoad())
            loadPosterViewHolder.relativeLayoutLoad.setVisibility(View.VISIBLE);
        else
            loadPosterViewHolder.relativeLayoutLoad.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return items.size() + FOOTER_SIZE;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == (getItemCount() - 1)){
            return FOOTER_TYPE;
        } else{
            return ITEM_TYPE;
        }
    }
}
