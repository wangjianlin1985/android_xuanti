package com.mobileserver.domain;

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
    private int subjectTypeObj;
    public int getSubjectTypeObj() {
        return subjectTypeObj;
    }
    public void setSubjectTypeObj(int subjectTypeObj) {
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
    private String teacherObj;
    public String getTeacherObj() {
        return teacherObj;
    }
    public void setTeacherObj(String teacherObj) {
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