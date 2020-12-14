package com.danilove.kimura.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.danilove.kimura.models.TvShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface TvShowDao {

    @Query("SELECT * FROM tvShows")
    Flowable<List<TvShow>> getWatchlist();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToWatchlist(TvShow tvShow);

    @Delete
    Completable removeFromWatchlist(TvShow tvShow);

}
