package com.example.baher.movieapp.models;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by baher on 9/20/16.
 */
@org.parceler.Parcel(Parcel.Serialization.BEAN)
@JsonObject(fieldNamingPolicy = JsonObject.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
public class Movie {
    @JsonField
    String poster_path;
    @JsonField
    boolean adult;
    @JsonField
    String overview;
    @JsonField
    String release_date;
    @JsonField
    ArrayList<Integer> genre_ids;
    @JsonField
   String id;
    @JsonField
    String original_title;
    @JsonField
    String original_language;
    @JsonField
    String title;
    @JsonField
    String backdrop_path;
    @JsonField
    double popularity;
    @JsonField
    int vote_count;
    @JsonField
    boolean video;
    @JsonField
    String vote_average;

    public Movie() {
    }

    public Movie(String posterPath, boolean adult, String overview, String releaseDate, ArrayList<Integer> gereIds, String id, String originalTitle, String originalLanguage, String title, String backdropPath, double popularity, int voteCount, boolean video, String voteAverage) {
        this.poster_path = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.release_date = releaseDate;
        this.genre_ids = gereIds;
        this.id = id;
        this.original_title = originalTitle;
        this.original_language = originalLanguage;
        this.title = title;
        this.backdrop_path = backdropPath;
        this.popularity = popularity;
        this.vote_count = voteCount;
        this.video = video;
        this.vote_average = voteAverage;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public void setPosterPath(String posterPath) {
        this.poster_path = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public void setReleaseDate(String releaseDate) {
        this.release_date = releaseDate;
    }

    public ArrayList<Integer> getGereIds() {
        return genre_ids;
    }

    public void setGereIds(ArrayList<Integer> gereIds) {
        this.genre_ids = gereIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return original_title;
    }

    public void setOriginalTitle(String originalTitle) {
        this.original_title = originalTitle;
    }

    public String getOriginalLanguage() {
        return original_language;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.original_language = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdrop_path;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdrop_path = backdropPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return vote_count;
    }

    public void setVoteCount(int voteCount) {
        this.vote_count = voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }
}
