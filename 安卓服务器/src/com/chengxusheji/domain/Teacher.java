package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Teacher {
    /*教师编号*/
    private String teacherNumber;
    public String getTeacherNumber() {
        return teacherNumber;
    }
    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    /*登录密码*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*姓名*/
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*性别*/
    private String sex;
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    /*出生日期*/
    private Timestamp birthday;
    public Timestamp getBirthday() {
        return birthday;
    }
    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    /*教师照片*/
    private String photo;
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /*职称*/
    private String professName;
    public String getProfessName() {
        return professName;
    }
    public void setProfessName(String professName) {
        this.professName = professName;
    }

    /*联系电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*家庭地址*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*入职日期*/
    private Timestamp inDate;
    public Timestamp getInDate() {
        return inDate;
    }
    public void setInDate(Timestamp inDate) {
        this.inDate = inDate;
    }

    /*教师简介*/
    private String introduce;
    public String getIntroduce() {
        return introduce;
    }
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

}