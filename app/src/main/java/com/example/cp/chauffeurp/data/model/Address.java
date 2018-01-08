package com.example.cp.chauffeurp.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by fanilogabaud on 07/01/2018.
 */

public class Address extends RealmObject {

    @PrimaryKey
    private long id;

    private String searchField;

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
