package com.mobileserver.domain;

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
    private int subjectObj;
    public int getSubjectObj() {
        return subjectObj;
    }
    public void setSubjectObj(int subjectObj) {
        this.subjectObj = subjectObj;
    }

    /*ѧ��*/
    private String studentObj;
    public String getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(String studentObj) {
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