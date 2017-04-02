package com.tumblrviewer.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sebastian on 10.12.2016.
 */

public class TumblrFeedResponse {

    @SerializedName("tumblelog")
    public Tumblelog mTumblelog;
    @SerializedName("posts-start")
    public int mPostsStart;
    @SerializedName("posts-total")
    public int mPostsTotal;
    @SerializedName("posts-type")
    public boolean mPostsType;
    @SerializedName("posts")
    public List<Posts> mPosts;

    public static class Feeds {
    }

    public static class Tumblelog {
        @SerializedName("title")
        public String mTitle;
        @SerializedName("description")
        public String mDescription;
        @SerializedName("name")
        public String mName;
        @SerializedName("timezone")
        public String mTimezone;
        @SerializedName("cname")
        public boolean mCname;
        @SerializedName("feeds")
        public List<Feeds> mFeeds;
    }

    public static class Posts {
        @SerializedName("id")
        public String mId;
        @SerializedName("url")
        public String mUrl;
        @SerializedName("type")
        public String mType;
        @SerializedName("date-gmt")
        public String mDateGmt;
        @SerializedName("date")
        public String mDate;
        @SerializedName("bookmarklet")
        public int mBookmarklet;
        @SerializedName("mobile")
        public int mMobile;
        @SerializedName("feed-item")
        public String mFeedItem;
        @SerializedName("from-feed-id")
        public int mFromFeedId;
        @SerializedName("unix-timestamp")
        public int mUnixTimestamp;
        @SerializedName("format")
        public String mFormat;
        @SerializedName("slug")
        public String mSlug;
        @SerializedName("is-submission")
        public boolean mIsSubmission;
        @SerializedName("width")
        public int mWidth;
        @SerializedName("height")
        public int mHeight;
        @SerializedName("photo-url-1280")
        public String mPhotoUrl_1280;
        @SerializedName("photo-url-500")
        public String mPhotoUrl_500;
        @SerializedName("photo-url-400")
        public String mPhotoUrl_400;
        @SerializedName("photo-url-250")
        public String mPhotoUrl_250;
        @SerializedName("photo-url-100")
        public String mPhotoUrl_100;
        @SerializedName("photo-url-75")
        public String mPhotoUrl_75;
        @SerializedName("regular-body")
        public String mRegularBody;

        @SerializedName("quote-text")
        public String mQuote;

        @SerializedName("tags")
        public List<String> mTags;

        @Override
        public String toString() {
            return "Posts{" +
                    "mId='" + mId + '\'' +
                    ", mType='" + mType + '\'' +
                    '}';
        }
    }
}
