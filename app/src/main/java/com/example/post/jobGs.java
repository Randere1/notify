package com.example.post;

public class jobGs {
    private  String Pic;
    private String uid;
    private String date;
    private String time;
    private String description;
    private String location;
    private String compare;
    private String jobtype;
    private String fullName;
    private String ProfileImage;
    private String adress;

    public jobGs(String adress) {
        this.setAdress(adress);
    }

    public jobGs(String pic, String uid, String date, String time, String description, String location, String compare, String jobtype, String fullName, String profileImage) {
        setPic(pic);
        this.setUid(uid);
        this.setDate(date);
        this.setTime(time);
        this.setDescription(description);
        this.setLocation(location);
        this.setCompare(compare);
        this.setJobtype(jobtype);
        this.setFullName(fullName);
        setProfileImage(profileImage);
    }

    public jobGs() {
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
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

    public String getCompare() {
        return compare;
    }

    public void setCompare(String compare) {
        this.compare = compare;
    }

    public String getJobtype() {
        return jobtype;
    }

    public void setJobtype(String jobtype) {
        this.jobtype = jobtype;
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
