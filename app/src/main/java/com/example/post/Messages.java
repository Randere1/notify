package com.example.post;

public class Messages {
    private String message;
    private String uid;
    private String type;
    private String from;
    private String to;
    private String messageID;
    private String time;
    private String date;

    public Messages(String message, String uid, String type, String from, String to, String messageID, String time, String date) {
        this.setMessage(message);
        this.setUid(uid);
        this.setType(type);
        this.setFrom(from);
        this.setTo(to);
        this.setMessageID(messageID);
        this.setTime(time);
        this.setDate(date);
    }

    public Messages() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
