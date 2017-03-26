package com.victorldavila.funnyguide.presenters;

import com.victorldavila.funnyguide.models.Genre;
import com.victorldavila.funnyguide.models.NetWorkError;
import com.victorldavila.funnyguide.models.ResponseGenre;
import com.victorldavila.funnyguide.repository.GenreRepository;
import com.victorldavila.funnyguide.view.ResponseView;

import rx.Subscription;

/**
 * Created by victor on 10/12/2016.
 */

public class GenrePresenter extends BaseRxPresenter implements FragmentPresenter<ResponseView<Genre>>, RxResponse<ResponseGenre> {

    private ResponseView<Genre> view;
    private GenreRepository genreRepository;

    private Subscription genreSubscription;

    public GenrePresenter(GenreRepository genreRepository){
        this.genreRepository = genreRepository;
    }

    @Override
    public void onViewCreated() {
        if(view == null)
            throw new NullViewException();

        getGenre();
    }

    @Override
    public void onDestroyView() {
        rxUnSubscribe();
    }

    public void getGenre(){
        rxUnSubscribe(genreSubscription);
        if(genreRepository != null) {
            genreSubscription = genreRepository.getMovieGenre(this);
            addSubscription(genreSubscription);
        }
    }

    @Override
    public void onNext(ResponseGenre result) {
        view.onItemList(result.getGenres());
    }

    @Override
    public void onError(NetWorkError error) {
        view.onError(error.getStatus_message());
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void addView(ResponseView<Genre> view) {
        this.view = view;
    }
}
