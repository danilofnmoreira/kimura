package com.danilove.kimura.network;

import com.danilove.kimura.responses.TvShowDetailsResponse;
import com.danilove.kimura.responses.TvShowsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("most-popular")
    Call<TvShowsResponse> getMostPopularTVShows(@Query("page") long page);

    @GET("show-details")
    Call<TvShowDetailsResponse> findTvShowDetailsResponse(@Query("q") long tvShowId);

    @GET("search")
    Call<TvShowsResponse> searchTvShow(@Query("q") String query, @Query("page") long page);

}
