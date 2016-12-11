package com.tumblrviewer.events;

/**
 * Created by sebastian on 10.12.2016.
 */

public class UserFeedPostEvent {
    public enum UserFeedTypesPostEvent {
        FEED_PHOTO
    }

    private Object data;
    private UserFeedTypesPostEvent code;

    public UserFeedPostEvent(Object data, UserFeedTypesPostEvent code) {
        this.data = data;
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public UserFeedTypesPostEvent getCode() {
        return code;
    }

    public void setCode(UserFeedTypesPostEvent code) {
        this.code = code;
    }
}
