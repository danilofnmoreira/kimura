package com.danilove.kimura.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class Episode {

    @SerializedName("season")
    private long season;

    @SerializedName("episode")
    private long episode;

    @SerializedName("name")
    private String name;

    @SerializedName("air_date")
    private String airDate;
}
