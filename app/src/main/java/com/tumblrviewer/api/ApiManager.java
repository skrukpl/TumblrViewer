package com.tumblrviewer.api;

import com.github.simonpercic.oklog3.OkLogInterceptor;
import com.tumblrviewer.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sebastian on 10.12.2016.
 */

public class ApiManager {
    public static final String API_BASE_URL = "https://www.tumblr.com/";
    private Retrofit mRetrofit;
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public Retrofit getRetrofit() {
        if (mRetrofit == null) {
            if (BuildConfig.REPORTLOGS) {
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkLogInterceptor interceptor = OkLogInterceptor.builder()
                        .build();

                httpClient.addInterceptor(interceptor);
                httpClient.addNetworkInterceptor(logging);

                mRetrofit = new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();
            } else {
                mRetrofit = new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        }
        return mRetrofit;
    }
}
