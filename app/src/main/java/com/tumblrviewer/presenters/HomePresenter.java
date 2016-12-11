package com.tumblrviewer.presenters;

import com.tumblrviewer.R;
import com.tumblrviewer.activities.MainActivity;
import com.tumblrviewer.controllers.HomeFragmentController;
import com.tumblrviewer.helpers.Utils;
import com.tumblrviewer.models.ProfilesDbModel;
import com.tumblrviewer.models.TumblrFeedResponse;
import com.tumblrviewer.view.IHomeFragment;

/**
 * Created by sebastian on 10.12.2016.
 */

public class HomePresenter implements HomeFragmentController.ResultCallback {
    private IHomeFragment mView;
    private MainActivity mainActivity;
    private HomeFragmentController mHomeFragmentController;
    private int mPostStart;
    private int mPostTotal;
    private boolean mStart = false;

    public HomePresenter() {
        mHomeFragmentController = new HomeFragmentController();
        mPostStart = 0;
        mPostTotal = 0;
        mStart = true;
    }

    public void onViewAttached(IHomeFragment view, MainActivity mainActivity) {
        mView = view;
        this.mainActivity = mainActivity;
        prepareToPopulateAc();
        if(mStart == true){
            String profile = Utils.getFromPreferences("profile", mainActivity);
            if(profile!=null)
                getNewFeedList(mainActivity, profile);
            mStart = false;
        }


    }

    public void onViewDettached() {
        mView = null;
        mainActivity = null;
    }

    private void prepareToPopulateAc(){
        mView.populateProfileAcAdapter(ProfilesDbModel.getAllRecords());
    }

    public void getNewFeedList(MainActivity mainActivity, String user){
        if(Utils.isNetworkAvailable(mainActivity)){
            mView.onShowProgressBar(true);
            mPostStart = 0;
            mPostTotal = 0;
            mHomeFragmentController.getUserFeeds(mainActivity, user, mPostStart, this);
        }else {
            mView.onFeedReceiveFail(mainActivity.getString(R.string.toast_no_connection));
        }
    }
    public void getFeedList(MainActivity mainActivity, String user){
        if(Utils.isNetworkAvailable(mainActivity)){
            mView.onShowProgressBar(true);
            mHomeFragmentController.getUserFeeds(mainActivity, user, mPostStart, this);
        }else {
            mView.onFeedReceiveFail(mainActivity.getString(R.string.toast_no_connection));
        }
    }

    @Override
    public void onSuccess(TumblrFeedResponse response) {
        mView.onShowProgressBar(false);
        if(response.mPostsStart==0)
            ProfilesDbModel.addRecord(response.mTumblelog.mName);

        Utils.storeInPreferences("profile", response.mTumblelog.mName, mainActivity);
        mView.setProfileAcText(response.mTumblelog.mName);

        mPostStart = response.mPostsStart+20;
        mPostTotal = response.mPostsTotal;
        mView.onFeedReceived(response);
    }

    @Override
    public void onError(int message) {
        mView.onShowProgressBar(false);
        switch (message){
            case 404:
                mView.onFeedReceiveFail(mainActivity.getString(R.string.toast_no_user));
                break;
            case 0:
            case -1:
                mView.onFeedReceiveFail(mainActivity.getString(R.string.toast_error));
                break;
        }
    }
}
