package com.danilove.kimura.responses;

import com.danilove.kimura.models.TvShow;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TvShowsResponse {

    @SerializedName("total")
    private String total;
    @SerializedName("page")
    private long page;
    @SerializedName("pages")
    private long pages;
    @SerializedName("tv_shows")
    private List<TvShow> tvShows;
}
