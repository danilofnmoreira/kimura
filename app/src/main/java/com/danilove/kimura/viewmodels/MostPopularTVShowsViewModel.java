package com.danilove.kimura.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.danilove.kimura.repositories.MostPopularTVShowsRepository;
import com.danilove.kimura.responses.TvShowsResponse;

public class MostPopularTVShowsViewModel extends ViewModel {

    private MostPopularTVShowsRepository mostPopularTVShowsRepository;

    public MostPopularTVShowsViewModel() {
        mostPopularTVShowsRepository = new MostPopularTVShowsRepository();

    }

    public LiveData<TvShowsResponse> getMostPopularTVShows(long page) {

        return mostPopularTVShowsRepository.getMostPopularTVShows(page);
    }

}
