package com.github.denisura.giphy.ui.item;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.github.denisura.giphy.R;
import com.github.denisura.giphy.ui.SingleFragmentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GiphyItemActivity extends SingleFragmentActivity {

    final static String LOG_TAG = GiphyItemActivity.class.getCanonicalName();
    final static String EXTRA_GIF_URL = "ItemActivity_extra_profile_id";

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;


    @Override
    protected Fragment createFragment() {
        String gifUrl = getIntent().getStringExtra(EXTRA_GIF_URL);
        return GiphyItemFragment.newInstance(gifUrl);
    }


    public static Intent newIntent(Context packageContext, String gifUrl) {
        Log.d(LOG_TAG, "Create intent with gifUrl " + gifUrl);
        Intent intent = new Intent(packageContext, GiphyItemActivity.class);
        intent.putExtra(EXTRA_GIF_URL, gifUrl);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
