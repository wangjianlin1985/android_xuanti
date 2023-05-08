package com.chengxusheji.domain;

import java.sql.Timestamp;
public class SelectItem {
    /*选题id*/
    private int selectItemId;
    public int getSelectItemId() {
        return selectItemId;
    }
    public void setSelectItemId(int selectItemId) {
        this.selectItemId = selectItemId;
    }

    /*题目*/
    private Subject subjectObj;
    public Subject getSubjectObj() {
        return subjectObj;
    }
    public void setSubjectObj(Subject subjectObj) {
        this.subjectObj = subjectObj;
    }

    /*学生*/
    private Student studentObj;
    public Student getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }

    /*选题时间*/
    private String selectTime;
    public String getSelectTime() {
        return selectTime;
    }
    public void setSelectTime(String selectTime) {
        this.selectTime = selectTime;
    }

}