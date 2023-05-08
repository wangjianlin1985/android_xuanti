package com.mobileclient.domain;

import java.io.Serializable;

public class SubjectType implements Serializable {
    /*类型编号*/
    private int subjectTypeId;
    public int getSubjectTypeId() {
        return subjectTypeId;
    }
    public void setSubjectTypeId(int subjectTypeId) {
        this.subjectTypeId = subjectTypeId;
    }

    /*类型名称*/
    private String subjectTypeName;
    public String getSubjectTypeName() {
        return subjectTypeName;
    }
    public void setSubjectTypeName(String subjectTypeName) {
        this.subjectTypeName = subjectTypeName;
    }

}