package com.taweewong.mylazyinstagram.model;

public class FollowRequest {
    private String user;
    private Boolean isFollow;

    public FollowRequest(String user, Boolean isFollow) {
        this.user = user;
        this.isFollow = isFollow;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Boolean getFollow() {
        return isFollow;
    }

    public void setFollow(Boolean follow) {
        isFollow = follow;
    }
}
