package com.danilove.kimura.responses;

import com.danilove.kimura.models.TvShowDetails;
import com.google.gson.annotations.SerializedName;

public class TvShowDetailsResponse {

    @SerializedName("tvShow")
    private TvShowDetails tvShow;

    public TvShowDetails getTvShow() {
        return tvShow;
    }

    public void setTvShow(TvShowDetails tvShow) {
        this.tvShow = tvShow;
    }
}
