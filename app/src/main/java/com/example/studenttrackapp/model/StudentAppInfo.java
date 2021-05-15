package com.example.studenttrackapp.model;

public class StudentAppInfo {
    String id;
    String appName;
    String createTime;
    String stuId;
    String techId;
    String week;
    String courseName;

    public StudentAppInfo(String id, String appName, String createTime, String stuId, String techId, String week, String courseName) {
        this.id = id;
        this.appName = appName;
        this.createTime = createTime;
        this.stuId = stuId;
        this.techId = techId;
        this.week = week;
        this.courseName = courseName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getTechId() {
        return techId;
    }

    public void setTechId(String techId) {
        this.techId = techId;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
