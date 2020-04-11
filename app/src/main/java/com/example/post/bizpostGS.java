package com.example.post;

import java.io.Serializable;

public class bizpostGS implements Serializable {
    private String uid;
    private String date;
    private String time;
    private String description;
    private String saleVenue;
    private String productName;
    private String value;
    private String contact;

    public bizpostGS(String contact) {
        this.setContact(contact);
    }

    public bizpostGS(String uid, String date, String time, String description, String saleVenue, String productName, String value, String fullName, String profileImage, String pic, String compare) {
        this.uid = uid;
        this.date = date;
        this.time = time;
        this.description = description;
        this.saleVenue = saleVenue;
        this.productName = productName;
        this.value = value;
        this.fullName = fullName;
        ProfileImage = profileImage;
        Pic = pic;
        this.compare = compare;
    }

    private String fullName;
    private String ProfileImage;
    private String Pic;
    private String compare;

    public bizpostGS() {
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

    public String getSaleVenue() {
        return saleVenue;
    }

    public void setSaleVenue(String saleVenue) {
        this.saleVenue = saleVenue;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
