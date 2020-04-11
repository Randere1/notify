package com.example.post;

public class chiefGs {
    private String uid;
    private String date;
    private String time;
    private String description;
    private String Tittle;
    private String fullName;
    private String compare;
    private String ProfileImage;
    private String Pic;

    public chiefGs(String uid, String date, String time, String description, String tittle, String fullName, String compare, String profileImage, String pic) {
        this.setUid(uid);
        this.setDate(date);
        this.setTime(time);
        this.setDescription(description);
        setTittle(tittle);
        this.setFullName(fullName);
        this.setCompare(compare);
        setProfileImage(profileImage);
        setPic(pic);
    }

    public chiefGs() {
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

    public String getTittle() {
        return Tittle;
    }

    public void setTittle(String tittle) {
        Tittle = tittle;
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
