package com.tencent.weili.entity;

import java.util.Date;

public class Activity {

    private String name;

    private String creator;

    private Integer timeType;

    private String description;

    private Integer count;

    private String location;

    private Date startTime;

    private Date endTime;

    private Date deadline;

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }



    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "name='" + name + '\'' +
                ", creator='" + creator + '\'' +
                ", timeType=" + timeType +
                ", description='" + description + '\'' +
                ", count='" + count + '\'' +
                ", location='" + location + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", deadline=" + deadline +
                '}';
    }
}
