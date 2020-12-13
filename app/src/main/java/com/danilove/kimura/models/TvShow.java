package com.danilove.kimura.models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class TvShow {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("permalink")
    private String permalink;

    @SerializedName("start_date")
    private String startDate;

    @SerializedName("end_date")
    private String endDate;

    @SerializedName("country")
    private String country;

    @SerializedName("network")
    private String network;

    @SerializedName("status")
    private String status;

    @SerializedName("image_thumbnail_path")
    private String imageThumbnailPath;
}
