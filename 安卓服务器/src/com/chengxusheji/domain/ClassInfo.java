package com.chengxusheji.domain;

import java.sql.Timestamp;
public class ClassInfo {
    /*�༶���*/
    private String classNumber;
    public String getClassNumber() {
        return classNumber;
    }
    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    /*����רҵ*/
    private String specialName;
    public String getSpecialName() {
        return specialName;
    }
    public void setSpecialName(String specialName) {
        this.specialName = specialName;
    }

    /*�༶����*/
    private String className;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    /*��������*/
    private Timestamp startDate;
    public Timestamp getStartDate() {
        return startDate;
    }
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    /*������*/
    private String headTeacher;
    public String getHeadTeacher() {
        return headTeacher;
    }
    public void setHeadTeacher(String headTeacher) {
        this.headTeacher = headTeacher;
    }

}