package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Subject {
    /*��Ŀ���*/
    private int subjectId;
    public int getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    /*��Ŀ����*/
    private String subjectName;
    public String getSubjectName() {
        return subjectName;
    }
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /*��Ŀ����*/
    private SubjectType subjectTypeObj;
    public SubjectType getSubjectTypeObj() {
        return subjectTypeObj;
    }
    public void setSubjectTypeObj(SubjectType subjectTypeObj) {
        this.subjectTypeObj = subjectTypeObj;
    }

    /*��Ŀ����*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*��ѡ����*/
    private int studentNumber;
    public int getStudentNumber() {
        return studentNumber;
    }
    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    /*ָ����ʦ*/
    private Teacher teacherObj;
    public Teacher getTeacherObj() {
        return teacherObj;
    }
    public void setTeacherObj(Teacher teacherObj) {
        this.teacherObj = teacherObj;
    }

    /*����ʱ��*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

}