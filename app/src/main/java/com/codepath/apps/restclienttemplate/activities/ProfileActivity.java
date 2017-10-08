package com.codepath.apps.restclienttemplate.activities;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.fragments.NavHeaderFragment;
import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;

import org.parceler.Parcel;

public class ProfileActivity extends AppCompatActivity implements NavHeaderFragment.followListener{

    private static final String USER = "currentUser";
    private static final String USER_TYPE = "user_type";
    User user;
    int userType = 1; // By default user profile

    public static final String FOLLOWITEM = "follow_item";
    public static final String USERID = "id";

    @Override
    public void OnUserProfileLoaded() {

    }

    @Override
    public void onFollowClicked(long ID) {
        Intent intent = new Intent();
        intent.putExtra(FOLLOWITEM,1);
        intent.putExtra(USERID,ID);
        intent.setClass(this,FriendsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFollowingClicked(long ID) {
        Intent intent = new Intent();
        intent.putExtra(FOLLOWITEM,2);
        intent.putExtra(USERID,ID);
        intent.setClass(this,FriendsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle bundle = getIntent().getBundleExtra("mybundle");
        user = bundle.getParcelable(USER);
            if(getIntent().hasExtra(USER_TYPE))
                userType = getIntent().getExtras().getInt(USER_TYPE);

        NavHeaderFragment userinfoFrag = NavHeaderFragment.newInstance(userType,user);
        UserTimelineFragment frag = UserTimelineFragment.newInstance(user.getScreen_name());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame1,userinfoFrag);
        ft.replace(R.id.frame2,frag);
        ft.commit();
    }
}
