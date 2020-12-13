package com.danilove.kimura.responses;

import com.danilove.kimura.models.TvShowDetails;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class TvShowDetailsResponse {

    @SerializedName("tvShow")
    private TvShowDetails tvShow;
}
