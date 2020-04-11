package com.example.post;

import java.io.Serializable;

public class all_commentsGs  implements Serializable {

    private  String Name;
    private String profile;
    private String comment;
    private String Time;
    private String Date;

    public all_commentsGs(String name, String profile, String comment, String time, String date) {
        setName(name);
        this.setProfile(profile);
        this.setComment(comment);
        setTime(time);
        setDate(date);
    }

    public all_commentsGs() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
