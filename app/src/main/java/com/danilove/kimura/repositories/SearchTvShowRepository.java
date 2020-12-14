package com.danilove.kimura.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.danilove.kimura.network.ApiClient;
import com.danilove.kimura.network.ApiService;
import com.danilove.kimura.responses.TvShowsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTvShowRepository {

    private ApiService apiService;

    public SearchTvShowRepository() {

        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TvShowsResponse> searchTvShow(String query, long page) {

        MutableLiveData<TvShowsResponse> data = new MutableLiveData<>();

        apiService.searchTvShow(query, page).enqueue(new Callback<TvShowsResponse>() {
            @Override
            public void onResponse(Call<TvShowsResponse> call, Response<TvShowsResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<TvShowsResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
