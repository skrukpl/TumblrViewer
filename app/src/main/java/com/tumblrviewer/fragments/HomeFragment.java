package com.tumblrviewer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.tumblrviewer.R;
import com.tumblrviewer.activities.FullScreenImageViewerActivity;
import com.tumblrviewer.activities.MainActivity;
import com.tumblrviewer.adapters.UserFeedHomeAdapter;
import com.tumblrviewer.events.UserFeedPostEvent;
import com.tumblrviewer.helpers.Utils;
import com.tumblrviewer.interfaces.ProgressBarInterface;
import com.tumblrviewer.models.TumblrFeedResponse;
import com.tumblrviewer.presenters.HomePresenter;
import com.tumblrviewer.view.IHomeFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sebastian on 10.12.2016.
 */

public class HomeFragment extends Fragment implements IHomeFragment, ProgressBarInterface {
    private MainActivity mActivity;
    private static HomeFragment sInstance;
    private static HomePresenter sHomePresenter;
    @BindView(R.id.tumbler_profile)
    AutoCompleteTextView mTumblerProfileAc;
    @BindView(R.id.search_iv)
    ImageView mSearchIv;
    @BindView(R.id.feed_rv)
    RecyclerView mFeedRv;
    @BindView(R.id.swipy_refresh_layout)
    SwipyRefreshLayout mSwipeRefreshLayout;

    private UserFeedHomeAdapter mUserFeedHomeAdapter;
    private LinearLayoutManager layoutManager;
    EventBus eventBus = EventBus.getDefault();

    public static HomeFragment getInstance() {
        if (sInstance == null)
            sInstance = new HomeFragment();
        return sInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        mActivity = (MainActivity) getActivity();

        sHomePresenter = new HomePresenter();
        mUserFeedHomeAdapter = null;

        prepareFeedRv();

        mTumblerProfileAc.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                    prepareFirstCall();
                }
                return false;
            }
        });

        return view;
    }

    private void prepareFeedRv(){
        layoutManager = new LinearLayoutManager(mActivity);
        mFeedRv.setLayoutManager(layoutManager);
        mFeedRv.setHasFixedSize(true);
        mFeedRv.setItemViewCacheSize(20);
        mFeedRv.setDrawingCacheEnabled(true);

        mSwipeRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTTOM);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
                    getPresenter().getFeedList(mActivity, mTumblerProfileAc.getText().toString());
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        eventBus.register(this);
        getPresenter().onViewAttached(this, mActivity);
    }

    @Override
    public void onPause() {
        super.onPause();
        eventBus.unregister(this);
        getPresenter().onViewDettached();
    }

    private void prepareFirstCall() {
        Utils.hideSoftKeyboard(mActivity);
        if (mUserFeedHomeAdapter != null)
            mUserFeedHomeAdapter.clear();
        getPresenter().getNewFeedList(mActivity, mTumblerProfileAc.getText().toString());
    }

    @OnClick({R.id.tumbler_profile, R.id.search_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tumbler_profile:
                break;
            case R.id.search_iv:
                prepareFirstCall();
                break;
        }
    }

    @Override
    public void onFeedReceived(TumblrFeedResponse postsList) {
        if (mUserFeedHomeAdapter == null) {
            mUserFeedHomeAdapter = new UserFeedHomeAdapter(mActivity, postsList.mPosts);
            mFeedRv.setAdapter(mUserFeedHomeAdapter);
        } else {
            mUserFeedHomeAdapter.addAll(postsList.mPosts);
            mSwipeRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void onFeedReceiveFail(String message) {
        mActivity.showToastMessage(message);
    }

    @Override
    public void populateProfileAcAdapter(List<String> profilesDbModelList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (mActivity, android.R.layout.simple_dropdown_item_1line, profilesDbModelList);
        mTumblerProfileAc.setAdapter(adapter);
        mTumblerProfileAc.setThreshold(1);
    }

    @Override
    public void setProfileAcText(String name) {
        mTumblerProfileAc.setText(name);
        mTumblerProfileAc.dismissDropDown();
        Utils.hideSoftKeyboard(mActivity);
    }

    @Override
    public void onShowProgressBar(boolean value) {
        showProgressBar(value);
    }


    private HomePresenter getPresenter() {
        return sHomePresenter;
    }

    @Override
    public void showProgressBar(boolean visibility) {
        mActivity.showProgressBar(visibility);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPhoto(UserFeedPostEvent event) {
        if (event.getCode() == UserFeedPostEvent.UserFeedTypesPostEvent.FEED_PHOTO) {
            Intent imageViewer = new Intent(mActivity, FullScreenImageViewerActivity.class);
            imageViewer.putExtra("bitmap", (String) event.getData());
            mActivity.startActivity(imageViewer);
        }
    }

}
