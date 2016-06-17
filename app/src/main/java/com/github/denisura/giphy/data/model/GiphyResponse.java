package com.github.denisura.giphy.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;

public class GiphyResponse {

    @SerializedName("data")
    @Expose
    private LinkedList<GifEntry> mEntries = new LinkedList<GifEntry>();

    /**
     *
     * @return
     * The mEntries
     */
    public LinkedList<GifEntry> getEntries() {
        return mEntries;
    }

    /**
     *
     * @param entries
     * The mEntries
     */
    public void setEntries(LinkedList<GifEntry> entries) {
        this.mEntries = entries;
    }

}