package com.example.post;

import java.io.Serializable;

public class contactsgs implements Serializable {
    private String Name;
    private String Status;
    private String uid;

    public contactsgs(String name, String status, String uid) {
        setName(name);
        setStatus(status);
        this.setUid(uid);
    }

    public contactsgs() {
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
