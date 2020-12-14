package com.danilove.kimura.responses;

import com.danilove.kimura.models.TvShow;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowsResponse {

    @SerializedName("total")
    private String total;
    @SerializedName("page")
    private long page;
    @SerializedName("pages")
    private long pages;
    @SerializedName("tv_shows")
    private List<TvShow> tvShows;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public List<TvShow> getTvShows() {
        return tvShows;
    }

    public void setTvShows(List<TvShow> tvShows) {
        this.tvShows = tvShows;
    }
}
