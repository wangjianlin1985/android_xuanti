package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Subject {
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
    private SubjectType subjectTypeObj;
    public SubjectType getSubjectTypeObj() {
        return subjectTypeObj;
    }
    public void setSubjectTypeObj(SubjectType subjectTypeObj) {
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
    private Teacher teacherObj;
    public Teacher getTeacherObj() {
        return teacherObj;
    }
    public void setTeacherObj(Teacher teacherObj) {
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