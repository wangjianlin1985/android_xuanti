package com.chengxusheji.domain;

import java.sql.Timestamp;
public class SubjectType {
    /*���ͱ��*/
    private int subjectTypeId;
    public int getSubjectTypeId() {
        return subjectTypeId;
    }
    public void setSubjectTypeId(int subjectTypeId) {
        this.subjectTypeId = subjectTypeId;
    }

    /*��������*/
    private String subjectTypeName;
    public String getSubjectTypeName() {
        return subjectTypeName;
    }
    public void setSubjectTypeName(String subjectTypeName) {
        this.subjectTypeName = subjectTypeName;
    }

}