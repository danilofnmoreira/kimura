package com.danilove.kimura.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.danilove.kimura.database.TvShowDatabase;
import com.danilove.kimura.models.TvShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class WatchlistViewModel extends AndroidViewModel {

    private TvShowDatabase tvShowDatabase;

    public WatchlistViewModel(@NonNull Application application) {
        super(application);
        tvShowDatabase = TvShowDatabase.getTvShowDatabase(application);
    }

    public Flowable<List<TvShow>> loadWatchlist() {

        return tvShowDatabase.tvShowDao().getWatchlist();
    }

    public Completable removeTvShowFromWatchlist(TvShow tvShow) {
        return tvShowDatabase.tvShowDao().removeFromWatchlist(tvShow);
    }
}
