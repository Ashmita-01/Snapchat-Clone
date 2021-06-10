package com.example.snapy.RecyclerViewFollow;

public class FollowObject {

    private String email;
    private String uid;
    private String profileImageUrl;

    public FollowObject(String username, String uid, String profileImageUrl) {
        this.email = email;
        this.uid = uid;
        this.profileImageUrl = profileImageUrl;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return email;
    }
    public void setUsername(String username) {
        this.email = email;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
