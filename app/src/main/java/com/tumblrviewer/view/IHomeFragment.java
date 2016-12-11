package com.tumblrviewer.view;

import com.tumblrviewer.models.TumblrFeedResponse;

import java.util.List;

/**
 * Created by sebastian on 10.12.2016.
 */

public interface IHomeFragment {

    void onFeedReceived(TumblrFeedResponse postsList);

    void onFeedReceiveFail(String message);

    void onShowProgressBar(boolean value);

    void populateProfileAcAdapter(List<String> profilesDbModelList);

    void setProfileAcText(String name);
}
