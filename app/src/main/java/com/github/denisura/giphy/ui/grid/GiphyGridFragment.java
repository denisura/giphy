package com.github.denisura.giphy.ui.grid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.github.denisura.giphy.R;
import com.github.denisura.giphy.data.model.GiphyResponse;
import com.github.denisura.giphy.data.api.GiphyApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class GiphyGridFragment extends Fragment {

    static final String LOG_TAG = GiphyGridFragment.class.getCanonicalName();

    @BindView(R.id.search_input)
    public EditText mSearchInput;

    @BindView(R.id.swipe_refresh_layout)
    public SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycler_view)
    public RecyclerView mRecyclerView;


    @BindView(R.id.empty_results_view)
    public TextView mEmptyResultsView;

    private ImageGridAdapter mSearchResultsAdapter;
    private PublishSubject<String> mSearchResultsSubject;

    public GiphyGridFragment() {
    }

    public static GiphyGridFragment newInstance() {
        return new GiphyGridFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_grid, container, false);
        ButterKnife.bind(this, rootView);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSearchResultsSubject.onNext(mSearchInput.getText().toString());
            }
        });
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        mSearchResultsAdapter = new ImageGridAdapter(getContext());
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(mSearchResultsAdapter);

        mSearchResultsSubject = PublishSubject.create();
        mSearchResultsSubject.debounce(100, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .map(new Func1<String, GiphyResponse>() {
                    @Override
                    public GiphyResponse call(String s) {
                        try {
                            if (s.equals("")) {
                                return new GiphyApi().getTrending();
                            } else {
                                return new GiphyApi().search(s);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GiphyResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(GiphyResponse images) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mSearchResultsAdapter.setEntries(images.getEntries());
                        if (images.getEntries().size() > 0) {
                            mEmptyResultsView.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                        } else {
                            mEmptyResultsView.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);
                        }
                    }
                });

        mSearchResultsSubject.onNext(mSearchInput.getText().toString());
        return rootView;
    }

    @OnTextChanged(R.id.search_input)
    void onTextChanged(CharSequence text) {
        mSwipeRefreshLayout.setRefreshing(true);
        mSearchResultsSubject.onNext(text.toString());
    }
}
