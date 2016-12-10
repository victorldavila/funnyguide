package com.victorldavila.funnyguide.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by victor on 10/12/2016.
 */

public class Genre extends RealmObject {
    @PrimaryKey
    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
