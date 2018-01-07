package com.example.cp.chauffeurp.data.model;

import io.realm.RealmObject;

/**
 * Created by fanilogabaud on 07/01/2018.
 */

public class Address extends RealmObject {

    private String searchField;

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }
}
