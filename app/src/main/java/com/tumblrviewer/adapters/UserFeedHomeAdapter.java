package com.tumblrviewer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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

public class UserFeedHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<TumblrFeedResponse.Posts> postsList = new ArrayList<>();

    public class ViewHolderQuote extends RecyclerView.ViewHolder {
        @BindView(R.id.feed_date_tv)
        TumblrTextView mFeedDateTv;
        @BindView(R.id.feed_quote_tv)
        TumblrTextView mFeedQuoteTv;
        @BindView(R.id.feed_tags_tv)
        TumblrTextView mFeedTagsTv;

        ViewHolderQuote(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class ViewHolderPhoto extends RecyclerView.ViewHolder {
        @BindView(R.id.feed_date_tv)
        TumblrTextView mFeedDateTv;
        @BindView(R.id.feed_photo_iv)
        ImageView mFeedPhotoIv;
        @BindView(R.id.feed_desc_tv)
        TumblrTextView mFeedDescTv;
        @BindView(R.id.feed_tags_tv)
        TumblrTextView mFeedTagsTv;

        ViewHolderPhoto(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.feed_photo_iv)
        public void onClick() {
            int position = getAdapterPosition();
            TumblrFeedResponse.Posts post = postsList.get(position);
            EventBus.getDefault().post(new UserFeedPostEvent(post.mPhotoUrl_1280, UserFeedPostEvent.UserFeedTypesPostEvent.FEED_PHOTO));
        }
    }

    public class ViewHolderRegular extends RecyclerView.ViewHolder {
        @BindView(R.id.feed_date_tv)
        TumblrTextView mFeedDateTv;
        @BindView(R.id.feed_regular_tv)
        TumblrTextView mFeedRegularTv;
        @BindView(R.id.feed_tags_tv)
        TumblrTextView mFeedTagsTv;

        ViewHolderRegular(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public UserFeedHomeAdapter(Context context, List<TumblrFeedResponse.Posts> postsList) {
        this.context = context;

        this.postsList = removeUnsuportedTypes(postsList);
    }

    private List<TumblrFeedResponse.Posts> removeUnsuportedTypes(List<TumblrFeedResponse.Posts> posts){
        for(int i=0; i<posts.size(); i++) {
            TumblrFeedResponse.Posts p = posts.get(i);
            if (p.mType.equals("quote") || p.mType.equals("photo") || p.mType.equals("regular"))
                continue;

            posts.remove(i);
            i--;
        }
//        Timber.i("postslist-size: "+posts.size());
//        for(int i=0; i<posts.size(); i++) {
//            Timber.i(posts.get(i).toString());
//        }

        return posts;
    }

    @Override
    public int getItemViewType(int position) {
        switch (postsList.get(position).mType) {
            case "quote":
                return 1;
            case "photo":
                return 2;
            case "regular":
                return 3;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case 1:
                return new ViewHolderQuote(layoutInflater.inflate(R.layout.adapter_home_feed_quote, parent, false));
            case 2:
                return new ViewHolderPhoto(layoutInflater.inflate(R.layout.adapter_home_feed_photo, parent, false));
            case 3:
                return new ViewHolderRegular(layoutInflater.inflate(R.layout.adapter_home_feed_regular, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TumblrFeedResponse.Posts post = postsList.get(position);
        switch (holder.getItemViewType()) {
            case 1:
                ViewHolderQuote viewHolderQuote = (ViewHolderQuote) holder;
                viewHolderQuote.mFeedDateTv.setText(post.mDate);
                viewHolderQuote.mFeedQuoteTv.setText(post.mQuote);
                break;
            case 2:
                ViewHolderPhoto viewHolderPhoto = (ViewHolderPhoto) holder;
                viewHolderPhoto.mFeedDateTv.setText(post.mDate);
                viewHolderPhoto.mFeedDescTv.setText(post.mSlug);
                if (post.mTags != null)
                    for (String tag : post.mTags)
                        viewHolderPhoto.mFeedTagsTv.setText("#" + tag);

                Glide.with(context)
                        .load(post.mPhotoUrl_500)
                        .placeholder(R.drawable.photo)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(viewHolderPhoto.mFeedPhotoIv);
                break;
            case 3:
                ViewHolderRegular viewHolderRegular = (ViewHolderRegular) holder;
                viewHolderRegular.mFeedDateTv.setText(post.mDate);
                viewHolderRegular.mFeedRegularTv.setText(Html.fromHtml(post.mRegularBody));
                break;
        }
    }

//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        TumblrFeedResponse.Posts post = postsList.get(position);
//        switch (post.mType) {
//            case "quote":
//                holder.mFeedQuoteTv.setVisibility(View.VISIBLE);
//                holder.mFeedPhotoIv.setVisibility(View.GONE);
//                holder.mFeedDescTv.setVisibility(View.GONE);
//                holder.mFeedDateTv.setText(post.mDate);
//                holder.mFeedDescTv.setText(post.mSlug);
//                holder.mFeedQuoteTv.setText(post.mQuote);
//                break;
//            case "photo":
//                holder.mFeedDescTv.setVisibility(View.VISIBLE);
//                holder.mFeedPhotoIv.setVisibility(View.VISIBLE);
//                holder.mFeedQuoteTv.setVisibility(View.GONE);
//                holder.mFeedDateTv.setText(post.mDate);
//                holder.mFeedDescTv.setText(post.mSlug);
//                if (post.mTags != null)
//                    for (int i = 0; i < post.mTags.size(); i++) {
//                        holder.mFeedTagsTv.setText("#" + post.mTags.get(i));
//                    }
//
//                Glide.with(context)
//                        .load(post.mPhotoUrl_500)
//                        .placeholder(R.drawable.photo)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .into(holder.mFeedPhotoIv);
//                break;
//        }
//        if (post.mSlug.equals(""))
//            holder.mFeedDescTv.setVisibility(View.GONE);
//        if (post.mTags != null) {
//            holder.mFeedTagsTv.setVisibility(View.VISIBLE);
//            StringBuilder stringBuilder = new StringBuilder();
//            for (int i = 0; i < post.mTags.size(); i++) {
//                stringBuilder.append("#" + post.mTags.get(i));
//            }
//            holder.mFeedTagsTv.setText(stringBuilder.toString());
//        } else
//            holder.mFeedTagsTv.setVisibility(View.GONE);
//    }


    public void clear() {
        postsList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<TumblrFeedResponse.Posts> list) {
//        for(int i=0; i<list.size(); i++) {
//            TumblrFeedResponse.Posts p = list.get(i);
//            if (p.mType.equals("quote") || p.mType.equals("photo") || p.mType.equals("regular")) {
//                Timber.i("Continue: "+p.mType.toString());
//                continue;
//            }else {
//                postsList.remove(i);
//                i--;
//            }
//        }
//        Timber.i("postslist-size: "+postsList.size());
//        for(int i=0; i<postsList.size(); i++) {
//            Timber.i(postsList.get(i).toString());
//        }
        postsList.addAll(removeUnsuportedTypes(list));
        notifyDataSetChanged();
    }

    public List<TumblrFeedResponse.Posts> postsListGet() {
        return postsList;
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }


}
