package com.danilove.kimura.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.danilove.kimura.database.TvShowDatabase;
import com.danilove.kimura.models.TvShow;
import com.danilove.kimura.repositories.TvShowDetailsRepository;
import com.danilove.kimura.responses.TvShowDetailsResponse;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class TVShowDetailsViewModel extends AndroidViewModel {

    private TvShowDetailsRepository tvShowDetailsRepository;
    private TvShowDatabase tvShowDatabase;

    public TVShowDetailsViewModel(@NonNull Application application) {
        super(application);
        tvShowDetailsRepository = new TvShowDetailsRepository();
        tvShowDatabase = TvShowDatabase.getTvShowDatabase(application);
    }

    public LiveData<TvShowDetailsResponse> findTvShowDetailsResponse(long tvShowId) {

        return tvShowDetailsRepository.findTvShowDetailsResponse(tvShowId);
    }

    public Completable addToWatchlist(TvShow tvShow) {
        return tvShowDatabase.tvShowDao().addToWatchlist(tvShow);
    }

    public Flowable<TvShow> getTvShowFromWatchlist(long tvShowId) {
        return tvShowDatabase.tvShowDao().getTvShowFromWatchlist(tvShowId);
    }

    public Completable removeFromWatchlist(TvShow tvShow) {
        return tvShowDatabase.tvShowDao().removeFromWatchlist(tvShow);
    }

}
