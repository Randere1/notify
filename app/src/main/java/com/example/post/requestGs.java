package com.example.post;

import java.io.Serializable;

public class requestGs  implements Serializable {
    private String Name;
    private String Status;
    private String profileImage;

    public requestGs() {
    }

    private String uid;
    private  String Accepted;

    public requestGs(String name, String status, String profileImage, String uid, String accepted) {
        setName(name);
        setStatus(status);
        this.setProfileImage(profileImage);
        this.setUid(uid);
        setAccepted(accepted);
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccepted() {
        return Accepted;
    }

    public void setAccepted(String accepted) {
        Accepted = accepted;
    }
}
