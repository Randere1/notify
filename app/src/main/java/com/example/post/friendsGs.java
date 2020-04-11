package com.example.post;

import java.io.Serializable;

public class friendsGs implements Serializable {
    private String FullName;
    private String status;
    private String ProfileImage;
    private String uid;

    public friendsGs(String fullName, String status, String profileImage, String uid) {
        setFullName(fullName);
        this.setStatus(status);
        setProfileImage(profileImage);
        this.setUid(uid);
    }

    public friendsGs() {
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
