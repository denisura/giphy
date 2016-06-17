package com.github.denisura.giphy.ui.grid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.denisura.giphy.R;
import com.github.denisura.giphy.data.model.GifEntry;
import com.github.denisura.giphy.ui.item.GiphyItemActivity;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.github.denisura.giphy.R.id.imageView;

/**
 * Adapter used to map a String to a text view.
 */
public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.ViewHolder> {

    private final Context mContext;

    private LinkedList<GifEntry> mEntries = new LinkedList<GifEntry>();


    public ImageGridAdapter(Context context) {
        mContext = context;
    }

    public void setEntries(LinkedList<GifEntry> entries) {
        mEntries.clear();
        mEntries.addAll(entries);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final String imgUrl = mEntries.get(position).getThumbnailUrl();

        Glide.with(mContext).load(imgUrl)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.mImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = GiphyItemActivity.newIntent(mContext,
                        mEntries.get(position).getOriginalImageUrl());
                if (android.os.Build.VERSION.SDK_INT >= 21) {
                    Bundle bundle = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(
                                    (Activity) mContext,
                                    holder.mImageView,
                                    holder.mImageView.getTransitionName()
                            )
                            .toBundle();
                    mContext.startActivity(intent, bundle);
                } else {
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEntries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(imageView)
        public ImageView mImageView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
