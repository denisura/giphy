package com.github.denisura.giphy.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Images {

    @SerializedName("fixed_height")
    @Expose
    private FixedHeight fixedHeight;

    @SerializedName("original")
    @Expose
    private Original original;

    /**
     * @return The fixedHeight
     */
    public FixedHeight getFixedHeight() {
        return fixedHeight;
    }

    /**
     * @param fixedHeight The fixed_height
     */
    public void setFixedHeight(FixedHeight fixedHeight) {
        this.fixedHeight = fixedHeight;
    }

    /**
     * @return The original
     */
    public Original getOriginal() {
        return original;
    }

    /**
     * @param original The original
     */
    public void setOriginal(Original original) {
        this.original = original;
    }
}