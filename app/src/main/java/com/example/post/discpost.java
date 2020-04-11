package com.example.post;

import java.io.Serializable;

public class discpost implements Serializable {

    private String uid;
    private String date;
    private String time;
    private String description;
    private String fullName;
    private String ProfileImage;
    private String Pic;
    private String compare;
    private String pk;

    public discpost(String uid, String date, String time, String description, String fullName, String profileImage, String pic, String compare, String pk, String pkk) {
        this.setUid(uid);
        this.setDate(date);
        this.setTime(time);
        this.setDescription(description);
        this.setFullName(fullName);
        setProfileImage(profileImage);
        setPic(pic);
        this.setCompare(compare);
        this.setPk(pk);
        this.setPkk(pkk);
    }

    private String pkk;

    public discpost() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }

    public String getCompare() {
        return compare;
    }

    public void setCompare(String compare) {
        this.compare = compare;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getPkk() {
        return pkk;
    }

    public void setPkk(String pkk) {
        this.pkk = pkk;
    }
}
