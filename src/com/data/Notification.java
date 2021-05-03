package com.data;

import java.io.Serializable;
import java.util.Date;

public class Notification implements Serializable {
    private String message;
    private Date timeToBeSend;

    public Notification(String message, Date date) {
        this.message = message;
        this.timeToBeSend = date;
    }

    public String getMessage() {
        return message;
    }
    public Date getDate() {
        return timeToBeSend;
    }
}
