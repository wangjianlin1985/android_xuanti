package com.mobileclient.domain;

import java.io.Serializable;

public class SelectItem implements Serializable {
    /*选题id*/
    private int selectItemId;
    public int getSelectItemId() {
        return selectItemId;
    }
    public void setSelectItemId(int selectItemId) {
        this.selectItemId = selectItemId;
    }

    /*题目*/
    private int subjectObj;
    public int getSubjectObj() {
        return subjectObj;
    }
    public void setSubjectObj(int subjectObj) {
        this.subjectObj = subjectObj;
    }

    /*学生*/
    private String studentObj;
    public String getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(String studentObj) {
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