package com.example.cp.chauffeurp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mapbox.services.commons.geojson.Feature;

import java.util.List;

/**
 * Created by fanilogabaud on 07/01/2018.
 */

public class ReversePosition {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("query")
    @Expose
    private List<Double> query = null;
    @SerializedName("features")
    @Expose
    private List<Feature> features = null;
    @SerializedName("attribution")
    @Expose
    private String attribution;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Double> getQuery() {
        return query;
    }

    public void setQuery(List<Double> query) {
        this.query = query;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }
}
