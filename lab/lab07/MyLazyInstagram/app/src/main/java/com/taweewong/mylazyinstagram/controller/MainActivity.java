package com.taweewong.mylazyinstagram.controller;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.taweewong.mylazyinstagram.R;
import com.taweewong.mylazyinstagram.adapter.LargePostAdapter;
import com.taweewong.mylazyinstagram.adapter.PostAdapter;
import com.taweewong.mylazyinstagram.api.LazyInstragramAPI;
import com.taweewong.mylazyinstagram.fragment.SwitchUserDialogFragment;
import com.taweewong.mylazyinstagram.model.UserProfile;
import com.taweewong.mylazyinstagram.utility.ProgressIndicator;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    ToggleButton listViewButton;
    ToggleButton gridViewButton;
    ProgressIndicator progressIndicator;
    PostAdapter postAdapter;
    LargePostAdapter largePostAdapter;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressIndicator = new ProgressIndicator(this);
        progressIndicator.show();

        username = getUsername();
        getUserProfile(username);

        listViewButton = findViewById(R.id.listViewButton);
        gridViewButton = findViewById(R.id.gridViewButton);

        listViewButton.setOnClickListener(this);
        gridViewButton.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.switch_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_switch) {
            FragmentManager fragmentManager = getFragmentManager();
            SwitchUserDialogFragment.newInstance(username)
                .show(fragmentManager, "Switcher User");
        }
        return true;
    }

    private String getUsername() {
        String username = getIntent().getStringExtra("username");

        if (username == null) {
            return "android";
        }

        return username;
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
                    setMainActivityAdapter(userProfile);
                    progressIndicator.hide();
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {

            }
        });
    }

    private void setMainActivityAdapter(UserProfile userProfile) {
        postAdapter = new PostAdapter(MainActivity.this, userProfile.getPosts());
        largePostAdapter = new LargePostAdapter(MainActivity.this, userProfile);

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
        ToggleButton followButton = findViewById(R.id.followButton);

        usernameTextView.setText(userProfile.getUser());
        Glide.with(MainActivity.this).load(userProfile.getUrlProfile()).into(userImage);
        postTextView.setText(String.valueOf(userProfile.getPost()));
        followerTextView.setText(String.valueOf(userProfile.getFollower()));
        followingTextView.setText(String.valueOf(userProfile.getFollowing()));
        bioTextView.setText(userProfile.getBio());
        followButton.setChecked(userProfile.getFollow());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gridViewButton:
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                recyclerView.setAdapter(postAdapter);
                disable(gridViewButton);
                toggle(listViewButton);
                break;
            case R.id.listViewButton:
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(largePostAdapter);
                disable(listViewButton);
                toggle(gridViewButton);
        }
    }
}
