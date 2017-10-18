package com.taweewong.mylazyinstagram.api;

import com.taweewong.mylazyinstagram.model.FollowRequest;
import com.taweewong.mylazyinstagram.model.FollowResponse;
import com.taweewong.mylazyinstagram.model.UserProfile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LazyInstragramAPI {
    String BASE = "https://us-central1-retrofit-course.cloudfunctions.net";

    @GET("/getProfile")
    Call<UserProfile> getProfile(@Query("user") String user);

    @POST("/follow")
    Call<FollowResponse> getFollowResponse(@Body FollowRequest body);

}
