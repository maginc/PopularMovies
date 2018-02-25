package com.example.user.popularmoviesstage1.api;

import com.example.user.popularmoviesstage1.model.Movie;
import com.example.user.popularmoviesstage1.model.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Andris on 017 17.02.18.
 */

public interface APIMethods {
    @GET("movie/{sort_by}")
    Call<MovieList> getMovieList(@Path("sort_by") String sortBy,
                                 @Query("api_key") String apiKey);

    @GET("/3/movie/{id}")
    Call<Movie> getMovie(@Path("id") String id,
                         @Query("api_key") String apiKey);
}
