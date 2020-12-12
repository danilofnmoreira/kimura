package com.danilove.kimura.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.danilove.kimura.R;
import com.danilove.kimura.viewmodels.MostPopularTVShowsViewModel;

public class MainActivity extends AppCompatActivity {

    private MostPopularTVShowsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);
        getMostPopularTVShows();
    }

    private void getMostPopularTVShows() {

        viewModel
                .getMostPopularTVShows(0)
                .observe(this, response ->
                        Toast.makeText(
                                getApplicationContext(),
                                "total pages: " + response.getTotal(),
                                Toast.LENGTH_LONG).show());
    }

}