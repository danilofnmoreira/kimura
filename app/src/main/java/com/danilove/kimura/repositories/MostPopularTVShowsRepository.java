package com.danilove.kimura.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.danilove.kimura.network.ApiClient;
import com.danilove.kimura.network.ApiService;
import com.danilove.kimura.responses.TvShowsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTVShowsRepository {

    private ApiService apiService;

    public MostPopularTVShowsRepository() {

        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TvShowsResponse> getMostPopularTVShows(long page) {

        MutableLiveData<TvShowsResponse> data = new MutableLiveData<>();

        apiService.getMostPopularTVShows(page).enqueue(new Callback<TvShowsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowsResponse> call, @NonNull Response<TvShowsResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowsResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
