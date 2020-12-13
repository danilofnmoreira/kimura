package com.danilove.kimura.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.danilove.kimura.R;
import com.danilove.kimura.databinding.ActivityTvShowDetailsBinding;
import com.danilove.kimura.viewmodels.TVShowDetailsViewModel;

public class TvShowDetailsActivity extends AppCompatActivity {

    private ActivityTvShowDetailsBinding activityTvShowDetailsBinding;
    private TVShowDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTvShowDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_tv_show_details);
        doInitialization();
    }

    private void doInitialization() {

        viewModel = new ViewModelProvider(this).get(TVShowDetailsViewModel.class);
        getTvShowDetails();
    }

    private void getTvShowDetails() {

        activityTvShowDetailsBinding.setIsLoading(true);
        long tvShowId = getIntent().getLongExtra("id", -1L);
        viewModel
                .findTvShowDetailsResponse(tvShowId)
                .observe(this, response -> {
                    activityTvShowDetailsBinding.setIsLoading(false);
                    Toast.makeText(getApplicationContext(), response.getTvShow().getUrl(), Toast.LENGTH_LONG).show();
                });
    }
}