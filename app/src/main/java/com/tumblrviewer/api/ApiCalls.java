package com.tumblrviewer.api;

import com.tumblrviewer.models.TumblrFeedResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by sebastian on 10.12.2016.
 */

public interface ApiCalls {
    @GET
    Call<TumblrFeedResponse> getFeedResponse(@Url String url);
}
