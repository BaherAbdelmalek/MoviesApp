package com.example.baher.movieapp.models;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by baher on 9/23/16.
 */
@org.parceler.Parcel(Parcel.Serialization.BEAN)
@JsonObject(fieldNamingPolicy = JsonObject.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
public class reviewsarray {
    @JsonField
    int page;
    @JsonField
    List<review> results;
    @JsonField
    int totalResults;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<review> getResults() {
        return results;
    }

    public void setResults(List<review> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public reviewsarray() {

    }

    @JsonField
    int totalPages;
}
