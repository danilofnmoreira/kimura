package com.danilove.kimura.listeners;

import com.danilove.kimura.models.TvShow;

public interface WatchlistListener {

    void onTvShowClicked(TvShow tvShow);

    void removeTvShowFromWatchlist(TvShow tvShow, int position);
}
