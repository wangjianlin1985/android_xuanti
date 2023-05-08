package com.mobileclient.domain;

import java.io.Serializable;

public class ClassInfo implements Serializable {
    /*班级编号*/
    private String classNumber;
    public String getClassNumber() {
        return classNumber;
    }
    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    /*所在专业*/
    private String specialName;
    public String getSpecialName() {
        return specialName;
    }
    public void setSpecialName(String specialName) {
        this.specialName = specialName;
    }

    /*班级名称*/
    private String className;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    /*成立日期*/
    private java.sql.Timestamp startDate;
    public java.sql.Timestamp getStartDate() {
        return startDate;
    }
    public void setStartDate(java.sql.Timestamp startDate) {
        this.startDate = startDate;
    }

    /*班主任*/
    private String headTeacher;
    public String getHeadTeacher() {
        return headTeacher;
    }
    public void setHeadTeacher(String headTeacher) {
        this.headTeacher = headTeacher;
    }

}