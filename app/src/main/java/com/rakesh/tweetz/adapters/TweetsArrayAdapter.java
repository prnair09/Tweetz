package com.rakesh.tweetz.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rakesh.tweetz.R;
import com.rakesh.tweetz.activities.ProfileActivity;
import com.rakesh.tweetz.helper.DateUtil;
import com.rakesh.tweetz.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by rparuthi on 3/29/2017.
 */

public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    public interface onProfileClickListener{
        public void onProfileImageClicked(Tweet tweet);
    }

    public TweetsArrayAdapter(Context context, List<Tweet> tweets){
        super(context, android.R.layout.simple_list_item_1,tweets);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //GetTweet
        Tweet tweet = getItem(position);

        TweetsViewHolder holder;

        //find or inflate the template
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
            holder = new TweetsViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (TweetsViewHolder)convertView.getTag();
        }

        holder.twTweetAuthorFullname.setText(tweet.getUser().getName());

        holder.twTweetAuthorScreenname.setText(tweet.getUser().getScreenName());

        holder.twTweetText.setText(tweet.getBody());

        holder.twTweetTimestamp.setText(DateUtil.getRelativeTimeAgo(tweet.getCreatedAt()));

        holder.twTweetauthorAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                intent.putExtra("ScreenName",tweet.getUser().getScreenName());
                getContext().startActivity(intent);

            }
        });

        holder.tvRetweetCount.setText(tweet.getRetweetCount());
        holder.tvFavoritesCount.setText(tweet.getFavoriteCount());


        Picasso.with(getContext()).
                load(tweet.getUser().getProfileImageUrl())
                .transform(new RoundedCornersTransformation(4,4))
                .into(holder.twTweetauthorAvatar);

        return convertView;
    }


    public class TweetsViewHolder {
        TextView twTweetRetweetedBy;
        ImageView twTweetauthorAvatar;
        TextView twTweetAuthorFullname;
        TextView twTweetAuthorScreenname;
        TextView twTweetTimestamp;
        TextView twTweetText;
        FrameLayout quoteTweetHolder;
        LinearLayout twTweetActionBar;
        View bottomSeparator;
        TextView tvRetweetCount;
        TextView tvFavoritesCount;




        public TweetsViewHolder(View view){

            twTweetRetweetedBy = (TextView) view.findViewById(R.id.tw__tweet_retweeted_by);

            twTweetauthorAvatar = (ImageView) view.findViewById(R.id.tw__tweet_author_avatar);

            twTweetAuthorFullname = (TextView) view.findViewById(R.id.tw__tweet_author_full_name);

            twTweetAuthorScreenname = (TextView) view.findViewById(R.id.tw__tweet_author_screen_name);

            twTweetTimestamp = (TextView) view.findViewById(R.id.tw__tweet_timestamp);

            twTweetText = (TextView) view.findViewById(R.id.tw__tweet_text);

            quoteTweetHolder = (FrameLayout) view.findViewById(R.id.quote_tweet_holder);

            //twTweetActionBar = (LinearLayout)view.findViewById(R.id.tw__tweet_action_bar);

            bottomSeparator = (View)view.findViewById(R.id.bottom_separator);

            tvRetweetCount = (TextView)view.findViewById(R.id.tvRetweets);

            tvFavoritesCount = (TextView)view.findViewById(R.id.tvFavorites);



        }
    }
}
