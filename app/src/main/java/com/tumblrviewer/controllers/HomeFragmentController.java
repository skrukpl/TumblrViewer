package com.tumblrviewer.controllers;

import com.tumblrviewer.activities.MainActivity;
import com.tumblrviewer.api.ApiCalls;
import com.tumblrviewer.models.TumblrFeedResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sebastian on 10.12.2016.
 */

public class HomeFragmentController {
    public interface ResultCallback<T> {
        void onSuccess(TumblrFeedResponse response);

        void onError(int message);
    }


    public void getUserFeeds(final MainActivity mainActivity, String nick, int start, final ResultCallback callback) {
        ApiCalls apiCalls = mainActivity.getApiManager().getRetrofit().create(ApiCalls.class);
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
    }
}
