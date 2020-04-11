package com.example.post;

import java.io.Serializable;

public class postMsg  implements Serializable {
    public postMsg() {
    }

    private String Name;
    private String Time;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public postMsg(String uid) {
        this.uid = uid;
    }

    public postMsg(String name, String time) {
        setName(name);
        setTime(time);
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
