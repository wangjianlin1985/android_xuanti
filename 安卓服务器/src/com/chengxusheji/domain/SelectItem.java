package com.chengxusheji.domain;

import java.sql.Timestamp;
public class SelectItem {
    /*ѡ��id*/
    private int selectItemId;
    public int getSelectItemId() {
        return selectItemId;
    }
    public void setSelectItemId(int selectItemId) {
        this.selectItemId = selectItemId;
    }

    /*��Ŀ*/
    private Subject subjectObj;
    public Subject getSubjectObj() {
        return subjectObj;
    }
    public void setSubjectObj(Subject subjectObj) {
        this.subjectObj = subjectObj;
    }

    /*ѧ��*/
    private Student studentObj;
    public Student getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }

    /*ѡ��ʱ��*/
    private String selectTime;
    public String getSelectTime() {
        return selectTime;
    }
    public void setSelectTime(String selectTime) {
        this.selectTime = selectTime;
    }

}