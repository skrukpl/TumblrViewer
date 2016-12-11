package com.tumblrviewer.activities;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.SuperToast;
import com.prashantsolanki.secureprefmanager.SecurePrefManagerInit;
import com.tumblrviewer.BuildConfig;
import com.tumblrviewer.R;
import com.tumblrviewer.api.ApiManager;
import com.tumblrviewer.helpers.Utils;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;


/**
 * Created by sebastian on 10.12.2016.
 */
public abstract class BaseActivity extends AppCompatActivity {
    ApiManager apiManager;
    SuperToast toast = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiManager = new ApiManager();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);

        new SecurePrefManagerInit.Initializer(getApplicationContext())
                .useEncryption(true)
                .initialize();

        if (BuildConfig.REPORTLOGS)
            Timber.plant(new Timber.DebugTree());
    }

    public ApiManager getApiManager() {
        return apiManager;
    }

    public void pushFragments(String tag, Fragment fragment, int content,
                              boolean shouldAnimate, boolean backstack) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (shouldAnimate)
            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        if (backstack)
            ft.addToBackStack(tag);
        ft.replace(content, fragment, tag);
        try {
            ft.commit();
            manager.executePendingTransactions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            Utils.hideSoftKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void showToastMessage(String message) {
        if (toast == null) {
            toast = SuperActivityToast.create(this, new Style(), Style.TYPE_STANDARD)
                    .setText(message)
                    .setDuration(Style.DURATION_MEDIUM)
                    .setFrame(Style.FRAME_KITKAT)
                    .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                    .setAnimations(Style.ANIMATIONS_POP);
            if (!toast.isShowing())
                toast.show();
            toast.setOnDismissListener(new SuperToast.OnDismissListener() {
                @Override
                public void onDismiss(View view, Parcelable token) {
                    toast = null;
                }
            });
        }
    }
}
