package com.tumblrviewer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tumblrviewer.R;
import com.tumblrviewer.events.UserFeedPostEvent;
import com.tumblrviewer.helpers.UI.TumblrTextView;
import com.tumblrviewer.models.TumblrFeedResponse;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sebastian on 10.12.2016.
 */

public class UserFeedHomeAdapter extends RecyclerView.Adapter<UserFeedHomeAdapter.ViewHolder> {
    Context context;
    List<TumblrFeedResponse.Posts> postsList = new ArrayList<>();

    public UserFeedHomeAdapter(Context context, List<TumblrFeedResponse.Posts> postsList) {
        this.context = context;
        this.postsList = postsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.adapter_home_feed_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TumblrFeedResponse.Posts post = postsList.get(position);
        switch (post.mType) {
            case "quote":
                holder.mFeedQuoteTv.setVisibility(View.VISIBLE);
                holder.mFeedPhotoIv.setVisibility(View.GONE);
                holder.mFeedDescTv.setVisibility(View.GONE);
                holder.mFeedDateTv.setText(post.mDate);
                holder.mFeedDescTv.setText(post.mSlug);


                holder.mFeedQuoteTv.setText(post.mQuote);
                break;
            case "photo":
                holder.mFeedDescTv.setVisibility(View.VISIBLE);
                holder.mFeedPhotoIv.setVisibility(View.VISIBLE);
                holder.mFeedQuoteTv.setVisibility(View.GONE);
                holder.mFeedDateTv.setText(post.mDate);
                holder.mFeedDescTv.setText(post.mSlug);
                if (post.mTags != null)
                    for (int i = 0; i < post.mTags.size(); i++) {
                        holder.mFeedTagsTv.setText("#" + post.mTags.get(i));
                    }

                Glide.with(context)
                        .load(post.mPhotoUrl_500)
                        .placeholder(R.drawable.photo)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.mFeedPhotoIv);
                break;
        }
        if (post.mSlug.equals(""))
            holder.mFeedDescTv.setVisibility(View.GONE);
        if (post.mTags != null) {
            holder.mFeedTagsTv.setVisibility(View.VISIBLE);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < post.mTags.size(); i++) {
                stringBuilder.append("#" + post.mTags.get(i));
            }
            holder.mFeedTagsTv.setText(stringBuilder.toString());
        } else
            holder.mFeedTagsTv.setVisibility(View.GONE);
    }


    public void clear() {
        postsList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<TumblrFeedResponse.Posts> list) {
        postsList.addAll(list);
        notifyDataSetChanged();
    }

    public List<TumblrFeedResponse.Posts> postsListGet() {
        return postsList;
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.feed_date_tv)
        TumblrTextView mFeedDateTv;
        @BindView(R.id.feed_photo_iv)
        ImageView mFeedPhotoIv;
        @BindView(R.id.feed_desc_tv)
        TumblrTextView mFeedDescTv;
        @BindView(R.id.feed_tags_tv)
        TumblrTextView mFeedTagsTv;
        @BindView(R.id.feed_quote_tv)
        TumblrTextView mFeedQuoteTv;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.feed_photo_iv)
        public void onClick() {
            int position = getAdapterPosition();
            TumblrFeedResponse.Posts post = postsList.get(position);
            EventBus.getDefault().post(new UserFeedPostEvent(post.mPhotoUrl_1280, UserFeedPostEvent.UserFeedTypesPostEvent.FEED_PHOTO));
        }
    }
}
