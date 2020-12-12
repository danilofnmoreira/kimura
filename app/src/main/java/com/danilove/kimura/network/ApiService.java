package com.danilove.kimura.network;

import com.danilove.kimura.responses.TvShowsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("most-popular")
    Call<TvShowsResponse> getMostPopularTVShows(@Query("page") long page);
}
