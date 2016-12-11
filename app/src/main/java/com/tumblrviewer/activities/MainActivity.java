package com.tumblrviewer.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.tumblrviewer.R;
import com.tumblrviewer.fragments.HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @BindView(R.id.fl_home_content)
    FrameLayout mFlHomeContent;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (fragment == null)
            fragment = HomeFragment.getInstance();
        pushFragments(fragment.getClass().getName(), fragment, R.id.fl_home_content, true, false);

    }

    public void showProgressBar(boolean visibility) {
        if (visibility)
            mProgressBar.setVisibility(View.VISIBLE);
        else
            mProgressBar.setVisibility(View.GONE);
    }
}
