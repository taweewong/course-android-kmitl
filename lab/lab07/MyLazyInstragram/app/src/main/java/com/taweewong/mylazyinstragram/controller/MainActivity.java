package com.taweewong.mylazyinstragram.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.taweewong.mylazyinstragram.R;
import com.taweewong.mylazyinstragram.adapter.PostAdapter;
import com.taweewong.mylazyinstragram.api.LazyInstragramAPI;
import com.taweewong.mylazyinstragram.model.Post;
import com.taweewong.mylazyinstragram.model.UserProfile;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{
    RecyclerView recyclerView;
    ToggleButton linearViewButton;
    ToggleButton gridViewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUserProfile("cartoon");

        linearViewButton = findViewById(R.id.linearViewButton);
        gridViewButton = findViewById(R.id.gridViewButton);

        linearViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearViewButton.isChecked()) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    disable(linearViewButton);
                    toggle(gridViewButton);
                }
            }
        });

        gridViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gridViewButton.isChecked()) {
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                    disable(gridViewButton);
                    toggle(linearViewButton);
                }
            }
        });
    }

    private void toggle(ToggleButton toggleButton) {
        toggleButton.setEnabled(true);
        toggleButton.setChecked(!toggleButton.isChecked());
    }

    private void disable(ToggleButton toggleButton) {
        toggleButton.setEnabled(false);
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
        recyclerView = findViewById(R.id.list);
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
