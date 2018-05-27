package com.tencent.weili.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(value = "userList")
public class Activity {

    private Integer id;

    @NotEmpty(message = "活动名称name字段不能为空")
    private String name;

    @NotEmpty(message = "活动创建者creator字段不能为空")
    private String creator;

    @NotNull(message = "timeType字段不能为空")
    private Integer timeType;

    @NotEmpty(message = "活动描述description字段不能为空")
    private String description;

    private Integer count;

    private Integer flag;
    @NotEmpty(message = "活动位置location字段不能为空")
    private String location;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future(message = "startTime字段需要时将来的时间")
    @NotNull(message = "startTime字段不能为空")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future(message = "endTime字段需要时将来的时间")
    @NotNull(message = "endTime字段不能为空")
    private Date endTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future(message = "deadline字段需要时将来的时间")
    @NotNull(message = "deadline字段不能为空")
    private Date deadline;


    private List<User> userList;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creator='" + creator + '\'' +
                ", timeType=" + timeType +
                ", description='" + description + '\'' +
                ", count=" + count +
                ", flag=" + flag +
                ", location='" + location + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", deadline=" + deadline +
                '}';
    }
}
