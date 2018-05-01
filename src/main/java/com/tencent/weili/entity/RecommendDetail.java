package com.tencent.weili.entity;

public class RecommendDetail {

    private Integer id;

    private Integer kind;

    private String title;

    private String detail;

    private String number;

    private String location;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "RecommendDetail{" +
                "id=" + id +
                ", kind=" + kind +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", number='" + number + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

}
