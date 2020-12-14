package com.danilove.kimura.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.danilove.kimura.R;
import com.danilove.kimura.adapters.EpisodesAdapter;
import com.danilove.kimura.adapters.ImageSliderAdapter;
import com.danilove.kimura.databinding.ActivityTvShowDetailsBinding;
import com.danilove.kimura.databinding.LayoutEpisodesBottomSheetBinding;
import com.danilove.kimura.models.TvShow;
import com.danilove.kimura.utilities.TemDataHolder;
import com.danilove.kimura.viewmodels.TVShowDetailsViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TvShowDetailsActivity extends AppCompatActivity {

    private static final String TAG = "TvShowDetailsActivity";

    private ActivityTvShowDetailsBinding activityTvShowDetailsBinding;
    private TVShowDetailsViewModel viewModel;
    private BottomSheetDialog episodesBottomSheetDialog;
    private LayoutEpisodesBottomSheetBinding layoutEpisodesBottomSheetBinding;
    private TvShow tvShow;
    private Boolean isTvShowAvailableInWatchlist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTvShowDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_tv_show_details);
        doInitialization();
    }

    private void doInitialization() {

        viewModel = new ViewModelProvider(this).get(TVShowDetailsViewModel.class);
        activityTvShowDetailsBinding.imageBack.setOnClickListener(v -> onBackPressed());
        tvShow = (TvShow) getIntent().getSerializableExtra("tvShow");
        checkTvShowInWatchlist();
        getTvShowDetails();
    }

    private void checkTvShowInWatchlist() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(
                viewModel
                        .getTvShowFromWatchlist(tvShow.getId())
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(tvShow1 -> {
                            isTvShowAvailableInWatchlist = true;
                            activityTvShowDetailsBinding.imageWatchlist.setImageResource(R.drawable.ic_added);
                            compositeDisposable.dispose();
                        })
        );
    }

    private void getTvShowDetails() {

        activityTvShowDetailsBinding.setIsLoading(true);
        long tvShowId = tvShow.getId();

        Log.d(TAG, "getTvShowDetails: tvShowId = " + tvShowId);

        viewModel
                .findTvShowDetailsResponse(tvShowId)
                .observe(this, response -> {
                    activityTvShowDetailsBinding.setIsLoading(false);
                    if (response.getTvShow() != null) {
                        if (response.getTvShow().getPictures() != null) {
                            loadImageSlider(response.getTvShow().getPictures());
                        }
                    }
                    activityTvShowDetailsBinding.setTvShowImageUrl(
                            response.getTvShow().getImagePath()
                    );
                    activityTvShowDetailsBinding.imageTvShow.setVisibility(View.VISIBLE);
                    activityTvShowDetailsBinding.setDescription(
                            String.valueOf(
                                    HtmlCompat.fromHtml(
                                            response.getTvShow().getDescription(),
                                            HtmlCompat.FROM_HTML_MODE_LEGACY
                                    )
                            )
                    );
                    activityTvShowDetailsBinding.textDescription.setVisibility(View.VISIBLE);
                    activityTvShowDetailsBinding.textReadMore.setVisibility(View.VISIBLE);
                    activityTvShowDetailsBinding.textReadMore.setOnClickListener(v -> {
                        if (activityTvShowDetailsBinding.textReadMore.getText().toString().equals("Read more")) {
                            activityTvShowDetailsBinding.textDescription.setMaxLines(Integer.MAX_VALUE);
                            activityTvShowDetailsBinding.textDescription.setEllipsize(null);
                            activityTvShowDetailsBinding.textReadMore.setText(R.string.read_less);
                        } else {
                            activityTvShowDetailsBinding.textDescription.setMaxLines(4);
                            activityTvShowDetailsBinding.textDescription.setEllipsize(TextUtils.TruncateAt.END);
                            activityTvShowDetailsBinding.textReadMore.setText(R.string.read_more);
                        }
                    });
                    activityTvShowDetailsBinding.setRating(
                            String.format(
                                    Locale.getDefault(),
                                    "%.2f",
                                    Double.parseDouble(response.getTvShow().getRating())
                            )
                    );
                    if (response.getTvShow().getGenres() != null && response.getTvShow().getGenres().size() > 0) {
                        activityTvShowDetailsBinding.setGenre((response.getTvShow().getGenres().get(0)));
                    } else {
                        activityTvShowDetailsBinding.setGenre("N/A");
                    }
                    activityTvShowDetailsBinding.setRuntime(response.getTvShow().getRuntime() + " Min");
                    activityTvShowDetailsBinding.viewDivider1.setVisibility(View.VISIBLE);
                    activityTvShowDetailsBinding.layoutMisc.setVisibility(View.VISIBLE);
                    activityTvShowDetailsBinding.viewDivider2.setVisibility(View.VISIBLE);
                    activityTvShowDetailsBinding.buttonWebsite.setOnClickListener(v -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(response.getTvShow().getUrl()));
                        startActivity(intent);
                    });
                    activityTvShowDetailsBinding.buttonWebsite.setVisibility(View.VISIBLE);
                    activityTvShowDetailsBinding.buttonEpisodes.setVisibility(View.VISIBLE);
                    activityTvShowDetailsBinding.buttonEpisodes.setOnClickListener(v -> {
                        if (episodesBottomSheetDialog == null) {
                            episodesBottomSheetDialog = new BottomSheetDialog(TvShowDetailsActivity.this);
                            layoutEpisodesBottomSheetBinding = DataBindingUtil.inflate(
                                    LayoutInflater.from(TvShowDetailsActivity.this),
                                    R.layout.layout_episodes_bottom_sheet,
                                    findViewById(R.id.episodesContainer),
                                    false
                            );
                            episodesBottomSheetDialog.setContentView(layoutEpisodesBottomSheetBinding.getRoot());
                            layoutEpisodesBottomSheetBinding.episodesRecyclerView.setAdapter(
                                    new EpisodesAdapter(response.getTvShow().getEpisodes())
                            );
                            layoutEpisodesBottomSheetBinding.textTitle.setText(
                                    String.format("Episode | %s", tvShow.getName())
                            );
                            layoutEpisodesBottomSheetBinding.imageClose.setOnClickListener(view -> episodesBottomSheetDialog.dismiss());
                        }

                        // optional section start
                        FrameLayout frameLayout = episodesBottomSheetDialog.findViewById(
                                com.google.android.material.R.id.design_bottom_sheet
                        );
                        if (frameLayout != null) {
                            BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
                            bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }
                        // optional section end

                        episodesBottomSheetDialog.show();
                    });

                    activityTvShowDetailsBinding.imageWatchlist.setOnClickListener(view -> {

                        CompositeDisposable compositeDisposable = new CompositeDisposable();
                        if(isTvShowAvailableInWatchlist) {
                            compositeDisposable.add(
                                    viewModel
                                            .removeFromWatchlist(tvShow)
                                            .subscribeOn(Schedulers.computation())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(() -> {
                                                isTvShowAvailableInWatchlist = false;
                                                TemDataHolder.IS_WATCHLIST_UPDATED = true;
                                                activityTvShowDetailsBinding.imageWatchlist.setImageResource(R.drawable.ic_watchlist);
                                                Toast.makeText(getApplicationContext(), "removed from watchlist", Toast.LENGTH_SHORT).show();
                                                compositeDisposable.dispose();
                                            })
                            );
                        } else {
                            compositeDisposable.add(
                                    viewModel
                                            .addToWatchlist(tvShow)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(() -> {
                                                TemDataHolder.IS_WATCHLIST_UPDATED = true;
                                                activityTvShowDetailsBinding.imageWatchlist.setImageResource(R.drawable.ic_added);
                                                Toast.makeText(getApplicationContext(), "added to watchlist", Toast.LENGTH_SHORT).show();
                                                compositeDisposable.dispose();
                                            })
                            );
                        }
                    });
                    activityTvShowDetailsBinding.imageWatchlist.setVisibility(View.VISIBLE);
                    loadBasicTvShowDetails();
                });
    }

    private void loadImageSlider(List<String> sliderImages) {
        activityTvShowDetailsBinding.sliderViewPager.setOffscreenPageLimit(1);
        activityTvShowDetailsBinding.sliderViewPager.setAdapter(new ImageSliderAdapter(sliderImages));
        activityTvShowDetailsBinding.sliderViewPager.setVisibility(View.VISIBLE);
        activityTvShowDetailsBinding.viewFadingEdge.setVisibility(View.VISIBLE);
        setupSliderIndicators(sliderImages.size());
        activityTvShowDetailsBinding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                setCurrentSliderIndicator(position);
            }
        });
    }

    private void setupSliderIndicators(int count) {
        ImageView[] indicators = new ImageView[count];

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );

        layoutParams.setMargins(8, 0, 8, 0);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.background_slider_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            activityTvShowDetailsBinding.layoutSliderIndicator.addView(indicators[i]);
        }
        activityTvShowDetailsBinding.layoutSliderIndicator.setVisibility(View.VISIBLE);
        setCurrentSliderIndicator(0);
    }

    private void setCurrentSliderIndicator(int position) {
        int childCount = activityTvShowDetailsBinding.layoutSliderIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) activityTvShowDetailsBinding.layoutSliderIndicator.getChildAt(i);

            if (i == position) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_active)
                );
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_inactive)
                );
            }
        }
    }

    private void loadBasicTvShowDetails() {
        activityTvShowDetailsBinding.setTvShowName(tvShow.getName());
        activityTvShowDetailsBinding.setStartedDate(tvShow.getStartDate());
        activityTvShowDetailsBinding.setNetworkCountry(
                String.format("%s (%s)", tvShow.getNetwork(), tvShow.getCountry())
        );
        activityTvShowDetailsBinding.setStatus(tvShow.getStatus());
        activityTvShowDetailsBinding.textName.setVisibility(View.VISIBLE);
        activityTvShowDetailsBinding.textNetworkCountry.setVisibility(View.VISIBLE);
        activityTvShowDetailsBinding.textStatus.setVisibility(View.VISIBLE);
        activityTvShowDetailsBinding.textStarted.setVisibility(View.VISIBLE);
    }
}