package com.example.cp.chauffeurp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by fanilogabaud on 07/01/2018.
 */

public class Feature {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("place_type")
    @Expose
    private List<String> placeType = null;
    @SerializedName("relevance")
    @Expose
    private Integer relevance;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("place_name")
    @Expose
    private String placeName;
    @SerializedName("center")
    @Expose
    private List<Double> center = null;
    @SerializedName("address")
    @Expose
    private Integer address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getPlaceType() {
        return placeType;
    }

    public void setPlaceType(List<String> placeType) {
        this.placeType = placeType;
    }

    public Integer getRelevance() {
        return relevance;
    }

    public void setRelevance(Integer relevance) {
        this.relevance = relevance;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public List<Double> getCenter() {
        return center;
    }

    public void setCenter(List<Double> center) {
        this.center = center;
    }

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }
}
