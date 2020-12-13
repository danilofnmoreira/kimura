package com.danilove.kimura.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

@Getter
public class TvShowDetails {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("permalink")
    private String permalink;

    @SerializedName("url")
    private String url;

    @SerializedName("description")
    private String description;

    @SerializedName("description_source")
    private String descriptionSource;

    @SerializedName("start_date")
    private String startDate;

    @SerializedName("end_date")
    private String endDate;

    @SerializedName("country")
    private String country;

    @SerializedName("status")
    private String status;

    @SerializedName("runtime")
    private long runtime;

    @SerializedName("network")
    private String network;

    @SerializedName("youtube_link")
    private String youtubeLink;

    @SerializedName("image_path")
    private String imagePath;

    @SerializedName("image_thumbnail_path")
    private String imageThumbnailPath;

    @SerializedName("rating")
    private String rating;

    @SerializedName("rating_count")
    private String ratingCount;

    private List<String> genres;

    private List<String> pictures;

    private List<Episode> episodes;
}
