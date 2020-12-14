package com.danilove.kimura.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.danilove.kimura.R;
import com.danilove.kimura.adapters.WatchlistAdapter;
import com.danilove.kimura.databinding.ActivityWatchlistBinding;
import com.danilove.kimura.listeners.WatchlistListener;
import com.danilove.kimura.models.TvShow;
import com.danilove.kimura.utilities.TemDataHolder;
import com.danilove.kimura.viewmodels.WatchlistViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchlistActivity extends AppCompatActivity implements WatchlistListener {

    private ActivityWatchlistBinding activityWatchlistBinding;
    private WatchlistViewModel viewModel;
    private WatchlistAdapter watchlistAdapter;
    private List<TvShow> watchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWatchlistBinding = DataBindingUtil.setContentView(this, R.layout.activity_watchlist);
        doInitialization();
    }

    private void doInitialization() {
        viewModel = new ViewModelProvider(this).get(WatchlistViewModel.class);
        activityWatchlistBinding.imageBack.setOnClickListener(view -> onBackPressed());
        watchlist = new ArrayList<>();
        loadWatchlist();
    }

    private void loadWatchlist() {
        activityWatchlistBinding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(
                viewModel
                        .loadWatchlist()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(tvShows -> {
                            activityWatchlistBinding.setIsLoading(false);
                            if (watchlist.size() > 0) {
                                watchlist.clear();
                            }
                            watchlist.addAll(tvShows);
                            watchlistAdapter = new WatchlistAdapter(watchlist, this);
                            activityWatchlistBinding.watchlistRecyclerView.setAdapter(watchlistAdapter);
                            activityWatchlistBinding.watchlistRecyclerView.setVisibility(View.VISIBLE);
                            compositeDisposable.dispose();
                        })
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(TemDataHolder.IS_WATCHLIST_UPDATED) {
            loadWatchlist();
            TemDataHolder.IS_WATCHLIST_UPDATED = false;
        }
    }

    @Override
    public void onTvShowClicked(TvShow tvShow) {

        Intent intent = new Intent(getApplicationContext(), TvShowDetailsActivity.class);
        intent.putExtra("tvShow", tvShow);
        startActivity(intent);
    }

    @Override
    public void removeTvShowFromWatchlist(TvShow tvShow, int position) {
        CompositeDisposable compositeDisposableForDelete = new CompositeDisposable();
        compositeDisposableForDelete.add(
                viewModel
                        .removeTvShowFromWatchlist(tvShow)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            watchlist.remove(position);
                            watchlistAdapter.notifyItemRemoved(position);
                            watchlistAdapter.notifyItemRangeChanged(position, watchlistAdapter.getItemCount());
                            compositeDisposableForDelete.dispose();
                        })
        );
    }
}