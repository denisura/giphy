package com.github.denisura.giphy.ui.item;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.denisura.giphy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.github.denisura.giphy.R.id.gifUrl;
import static com.github.denisura.giphy.R.id.imageView;
import static com.github.denisura.giphy.R.id.loading;

public class GiphyItemFragment extends Fragment {

    static final String LOG_TAG = GiphyItemFragment.class.getCanonicalName();

    private static final String ARG_GIF_URL = "ARG_GIF_URL";

    @BindView(imageView)
    public ImageView mImageView;


    @BindView(gifUrl)
    public TextView mGifUrl;


    @BindView(loading)
    public ProgressBar mLoading;

    public GiphyItemFragment() {
    }

    public static GiphyItemFragment newInstance(String gifUrl) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_GIF_URL, gifUrl);
        GiphyItemFragment fragment = new GiphyItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item, container, false);
        ButterKnife.bind(this, rootView);

        String gifUrl = getArguments().getString(ARG_GIF_URL);

        Glide.with(getContext()).load(gifUrl)
                .crossFade()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        // do something
                        Log.d(LOG_TAG,"onException");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        // do something
                        Log.d(LOG_TAG,"onResourceReady");
                        mLoading.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(mImageView);

        mGifUrl.setText(gifUrl);
        return rootView;
    }

}
