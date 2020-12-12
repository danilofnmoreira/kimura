package com.danilove.kimura.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.danilove.kimura.R;
import com.danilove.kimura.adapters.TvShowsAdapter;
import com.danilove.kimura.databinding.ActivityMainBinding;
import com.danilove.kimura.models.TvShow;
import com.danilove.kimura.viewmodels.MostPopularTVShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private MostPopularTVShowsViewModel viewModel;
    private List<TvShow> tvShows = new ArrayList<>();
    private TvShowsAdapter tvShowsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        doInitialization();
    }

    private void doInitialization() {
        activityMainBinding.tvShowRecyclerView.setHasFixedSize(true);
        this.viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);
        tvShowsAdapter = new TvShowsAdapter(tvShows);
        activityMainBinding.tvShowRecyclerView.setAdapter(tvShowsAdapter);
        getMostPopularTVShows();
    }

    private void getMostPopularTVShows() {

        activityMainBinding.setIsLoading(true);

        viewModel
                .getMostPopularTVShows(0)
                .observe(this, response ->
                {
                    activityMainBinding.setIsLoading(false);
                    if (response != null) {
                        if (response.getTvShows() != null) {
                            tvShows.addAll(response.getTvShows());
                            tvShowsAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

}