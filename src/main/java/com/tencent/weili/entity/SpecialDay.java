package com.tencent.weili.entity;

import org.junit.Test;

import java.util.Map;

public class SpecialDay {

    private String date;

    private String event;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "SpecialDay{" +
                "date=" + date +
                ", event='" + event + '\'' +
                '}';
    }

    public static void main(String[] args) {
        Object object = test("");
    }

    public static Object test(String str) {
        return new Object();
    }

}
