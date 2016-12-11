package com.victorldavila.funnyguide.models;

import java.util.ArrayList;

/**
 * Created by victo on 11/12/2016.
 */

public class ResponseTmdb {

    private ArrayList<Genre> genres;

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }
}
