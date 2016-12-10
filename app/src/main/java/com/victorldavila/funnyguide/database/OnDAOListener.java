package com.victorldavila.funnyguide.database;

import java.util.List;

/**
 * Created by victor on 10/12/2016.
 */

public interface OnDAOListener<T> {
    List<T> getItemList();
    void saveItem(T item);
    void storeItem(T item);
    void storeItemList(List<T> items);
    void deleteItem(T item);
    void clearDatabase();
}
