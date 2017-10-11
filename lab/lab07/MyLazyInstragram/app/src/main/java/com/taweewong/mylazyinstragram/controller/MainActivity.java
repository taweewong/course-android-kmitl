package com.taweewong.mylazyinstragram.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.taweewong.mylazyinstragram.R;
import com.taweewong.mylazyinstragram.adapter.PostAdapter;
import com.taweewong.mylazyinstragram.api.LazyInstragramAPI;
import com.taweewong.mylazyinstragram.model.UserProfile;
import com.taweewong.mylazyinstragram.model.Post;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUserProfile("cartoon");
    }

    private void getUserProfile(final String userName) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(LazyInstragramAPI.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LazyInstragramAPI lazyInstragramAPI = retrofit.create(LazyInstragramAPI.class);

        Call<UserProfile> call = lazyInstragramAPI.getProfile(userName);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful()) {
                    UserProfile userProfile = response.body();

                    setMainActivityDisplay(userProfile);
                    setMainActivityAdapter(userProfile.getPosts());
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {

            }
        });
    }

    private void setMainActivityAdapter(Post[] posts) {
        PostAdapter postAdapter = new PostAdapter(MainActivity.this, posts);
        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
        recyclerView.setAdapter(postAdapter);
    }

    private void setMainActivityDisplay(UserProfile userProfile) {
        TextView usernameTextView = findViewById(R.id.usernameTextView);
        ImageView userImage = findViewById(R.id.userImage);
        TextView postTextView = findViewById(R.id.postTextView);
        TextView followerTextView = findViewById(R.id.followerTextView);
        TextView followingTextView = findViewById(R.id.followingTextView);
        TextView bioTextView = findViewById(R.id.bioTextView);

        usernameTextView.setText(userProfile.getUser());
        Glide.with(MainActivity.this).load(userProfile.getUrlProfile()).into(userImage);
        postTextView.setText(String.valueOf(userProfile.getPost()));
        followerTextView.setText(String.valueOf(userProfile.getFollower()));
        followingTextView.setText(String.valueOf(userProfile.getFollowing()));
        bioTextView.setText(userProfile.getBio());
    }
}
