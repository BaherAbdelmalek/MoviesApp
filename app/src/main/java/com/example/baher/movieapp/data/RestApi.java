package com.example.baher.movieapp.data;

import com.example.baher.movieapp.models.Movies;
import com.example.baher.movieapp.models.TrailerArray;
import com.example.baher.movieapp.models.reviewsarray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by baher on 9/20/16.
 */
public interface RestApi {
    @GET("movie/top_rated")
    Call<Movies> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<TrailerArray> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
    @GET("movie/{id}/reviews")
    Call<reviewsarray> getMoviereviews(@Path("id") int id, @Query("api_key") String apiKey);
    @GET("movie/popular")
    Call<Movies> getMostPoupularMovies(@Query("api_key") String apiKey);
}
