package com.example.post;

public class chat {
    private String sender;
    private String reciver;
    private String message;
    private String uid;
    private String time;

    public chat(String sender, String reciver, String message, String uid, String time) {
        this.setSender(sender);
        this.setReciver(reciver);
        this.setMessage(message);
        this.setUid(uid);
        this.setTime(time);
    }

    public chat() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
