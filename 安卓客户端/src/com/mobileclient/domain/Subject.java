package com.mobileclient.domain;

import java.io.Serializable;

public class Subject implements Serializable {
    /*题目编号*/
    private int subjectId;
    public int getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    /*题目名称*/
    private String subjectName;
    public String getSubjectName() {
        return subjectName;
    }
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /*题目类型*/
    private int subjectTypeObj;
    public int getSubjectTypeObj() {
        return subjectTypeObj;
    }
    public void setSubjectTypeObj(int subjectTypeObj) {
        this.subjectTypeObj = subjectTypeObj;
    }

    /*题目内容*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*限选人数*/
    private int studentNumber;
    public int getStudentNumber() {
        return studentNumber;
    }
    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    /*指导老师*/
    private String teacherObj;
    public String getTeacherObj() {
        return teacherObj;
    }
    public void setTeacherObj(String teacherObj) {
        this.teacherObj = teacherObj;
    }

    /*发布时间*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

}