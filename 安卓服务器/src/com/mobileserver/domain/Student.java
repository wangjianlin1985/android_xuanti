package com.mobileserver.domain;

public class Student {
    /*ѧ��*/
    private String studentNumber;
    public String getStudentNumber() {
        return studentNumber;
    }
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    /*��¼����*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*���ڰ༶*/
    private String classObj;
    public String getClassObj() {
        return classObj;
    }
    public void setClassObj(String classObj) {
        this.classObj = classObj;
    }

    /*����*/
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*�Ա�*/
    private String sex;
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    /*��������*/
    private java.sql.Timestamp birthday;
    public java.sql.Timestamp getBirthday() {
        return birthday;
    }
    public void setBirthday(java.sql.Timestamp birthday) {
        this.birthday = birthday;
    }

    /*������ò*/
    private String zzmm;
    public String getZzmm() {
        return zzmm;
    }
    public void setZzmm(String zzmm) {
        this.zzmm = zzmm;
    }

    /*��ϵ�绰*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*��ͥ��ַ*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*������Ƭ*/
    private String photo;
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }

}