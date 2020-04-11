package com.example.post;

import java.io.Serializable;

public class houseGs implements Serializable {
    private String uid;
    private String date;
    private String time;
    private String description;
    private String location;
    private String HouseName;
    private String cost;
    private String contact;
    private String fullName;
    private String compare;
    private String ProfileImage;
    private String Pic;

    public houseGs(String uid, String date, String time, String description, String location, String houseName, String cost, String contact, String fullName, String compare, String profileImage, String pic) {
        this.setUid(uid);
        this.setDate(date);
        this.setTime(time);
        this.setDescription(description);
        this.setLocation(location);
        setHouseName(houseName);
        this.setCost(cost);
        this.setContact(contact);
        this.setFullName(fullName);
        this.setCompare(compare);
        setProfileImage(profileImage);
        setPic(pic);
    }

    public houseGs() {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHouseName() {
        return HouseName;
    }

    public void setHouseName(String houseName) {
        HouseName = houseName;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCompare() {
        return compare;
    }

    public void setCompare(String compare) {
        this.compare = compare;
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
}
