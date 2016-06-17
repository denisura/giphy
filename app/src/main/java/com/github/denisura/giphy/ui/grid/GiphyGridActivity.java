package com.github.denisura.giphy.ui.grid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.github.denisura.giphy.R;
import com.github.denisura.giphy.ui.SingleFragmentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GiphyGridActivity extends SingleFragmentActivity {

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    @Override
    protected Fragment createFragment() {
        return GiphyGridFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
    }
}
