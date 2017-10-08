package com.codepath.apps.restclienttemplate.activities;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.utils.PatternEditableBuilder;
import com.codepath.apps.restclienttemplate.utils.TwitterApplication;
import com.codepath.apps.restclienttemplate.utils.TwitterClient;
import com.codepath.apps.restclienttemplate.databinding.ActivityTweetDetailBinding;
import com.codepath.apps.restclienttemplate.fragments.ReplyFragment;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetDetailActivity extends AppCompatActivity implements ReplyFragment.ReplyTweetListener {

    public static final String TAG = "TweetDetailActivity";
    ImageView photo;
    TextView name;
    TextView body;
    Context mctx;
    ImageView Reply;
    FragmentManager fm;
    ImageView image_embed;

    TextView reply_count;
    TextView retweet_count;
    TextView fav_count;


    Toolbar toolbar;
    private ActivityTweetDetailBinding binding;
    Long tweet_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_tweet_detail);
        mctx = TweetDetailActivity.this;
        final Tweet tweet = getIntent().getParcelableExtra("Tweet");
        toolbar = binding.appbar.toolbar;
        setSupportActionBar(toolbar);

        tweet_id = tweet.gettweet_id();
        photo = binding.profileImageDetail;
        name = binding.nameDetail;
        body = binding.bodyDetail;
        Reply = binding.actionLayout.replyItemTweet;
        image_embed = binding.imageEmbed;
        reply_count = binding.actionLayout.replyCountText;
        retweet_count = binding.actionLayout.retweetCountText;
        fav_count = binding.actionLayout.likeCountText;

        name.setText(tweet.getUser().getName()+"\n@"+tweet.getUser().getScreen_name());
        body.setText(tweet.getBody());
        new PatternEditableBuilder().
                addPattern(Pattern.compile("\\@(\\w+)"), TweetDetailActivity.this.getResources().getColor(R.color.twitter_logo_blue),
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {
                                Toast.makeText(TweetDetailActivity.this, "Clicked username: " + text,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).into(body);
        new PatternEditableBuilder().
                addPattern(Pattern.compile("\\#(\\w+)"), TweetDetailActivity.this.getResources().getColor(R.color.twitter_logo_blue),
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {
                                Toast.makeText(TweetDetailActivity.this, "Clicked Hashtag: " + text,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).into(body);
        Glide.with(mctx).load(tweet.getUser().getProfile_imageURL()).bitmapTransform(new RoundedCornersTransformation(mctx,4,1, RoundedCornersTransformation.CornerType.ALL)).into(photo);

        fm = getSupportFragmentManager();
        Reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReplyFragment reply = new ReplyFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("Tweet",tweet);
                reply.setArguments(bundle);
                reply.show(fm,"reply_frag");
            }
        });


        if(tweet.getEntities()==null){
            image_embed.setVisibility(View.GONE);
        }
        else{
            image_embed.setVisibility(View.VISIBLE);
            Glide.with(mctx).load(tweet.getEntities().getMedia().getMedia_url()).into(image_embed);

        }

        reply_count.setText(""+tweet.getReply_count());

        retweet_count.setText(""+tweet.getRetweet_count());
        fav_count.setText(""+tweet.getFavorite_count());

    }


    @Override
    public void onReplyTweet() {
        Toast.makeText(mctx,"Tweet Replied",Toast.LENGTH_SHORT).show();
    }
}
