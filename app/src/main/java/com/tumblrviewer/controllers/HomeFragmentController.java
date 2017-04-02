package com.tumblrviewer.controllers;

import com.tumblrviewer.api.ApiCalls;
import com.tumblrviewer.api.ApiManager;
import com.tumblrviewer.models.TumblrFeedResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by sebastian on 10.12.2016.
 */

public class HomeFragmentController {
    ApiCalls apiCalls = ApiManager.getApiManager().getApi();

    public interface ResultCallback<T> {
        void onSuccess(TumblrFeedResponse response);

        void onError(int message);
    }


    public void getUserFeeds(String nick, int start, final ResultCallback callback) {
        if(!nick.equals(".") && !nick.equals("")) {
            Timber.i("Search for user: "+nick);
            String url = "https://" + nick + ".tumblr.com/api/read/json?debug=1&start=" + start;
            Call<TumblrFeedResponse> call = apiCalls.getFeedResponse(url);
            call.enqueue(new Callback<TumblrFeedResponse>() {
                @Override
                public void onResponse(Call<TumblrFeedResponse> call, Response<TumblrFeedResponse> response) {
                    if (response.code() == 200) {
                        callback.onSuccess(response.body());
                    } else if (response.code() == 404) {
                        callback.onError(404);
                    } else {
                        callback.onError(0);
                    }
                }

                @Override
                public void onFailure(Call<TumblrFeedResponse> call, Throwable t) {
                    callback.onError(-1);
                }
            });
        }else {
            callback.onError(0);
        }
    }
}
