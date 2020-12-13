package com.danilove.kimura.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.danilove.kimura.repositories.TvShowDetailsRepository;
import com.danilove.kimura.responses.TvShowDetailsResponse;

public class TVShowDetailsViewModel extends ViewModel {

    private TvShowDetailsRepository tvShowDetailsRepository;

    public TVShowDetailsViewModel() {
        tvShowDetailsRepository = new TvShowDetailsRepository();

    }

    public LiveData<TvShowDetailsResponse> findTvShowDetailsResponse(long tvShowId) {

        return tvShowDetailsRepository.findTvShowDetailsResponse(tvShowId);
    }

}
