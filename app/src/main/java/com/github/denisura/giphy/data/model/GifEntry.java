package com.github.denisura.giphy.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GifEntry {

    @SerializedName("images")
    @Expose
    private Images images;

    public String getOriginalImageUrl() {
        return images.getOriginal().getUrl();
    }

    public String getThumbnailUrl() {
        return images.getFixedHeight().getUrl();
    }
}