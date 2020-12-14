package com.danilove.kimura.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.danilove.kimura.repositories.SearchTvShowRepository;
import com.danilove.kimura.responses.TvShowsResponse;

public class SearchViewModel extends ViewModel {

    public SearchTvShowRepository searchTvShowRepository;

    public SearchViewModel() {
        searchTvShowRepository = new SearchTvShowRepository();
    }

    public LiveData<TvShowsResponse> searchTvShow(String query, long page) {

        return searchTvShowRepository.searchTvShow(query, page);
    }
}
