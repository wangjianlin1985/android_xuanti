package com.mobileserver.domain;

public class GuestBook {
    /*��¼id*/
    private int guestBookId;
    public int getGuestBookId() {
        return guestBookId;
    }
    public void setGuestBookId(int guestBookId) {
        this.guestBookId = guestBookId;
    }

    /*����*/
    private String qustion;
    public String getQustion() {
        return qustion;
    }
    public void setQustion(String qustion) {
        this.qustion = qustion;
    }

    /*����ѧ��*/
    private String student;
    public String getStudent() {
        return student;
    }
    public void setStudent(String student) {
        this.student = student;
    }

    /*����ʱ��*/
    private String questionTime;
    public String getQuestionTime() {
        return questionTime;
    }
    public void setQuestionTime(String questionTime) {
        this.questionTime = questionTime;
    }

    /*��ʦ�ظ�*/
    private String reply;
    public String getReply() {
        return reply;
    }
    public void setReply(String reply) {
        this.reply = reply;
    }

    /*�����ʦ*/
    private String teacherObj;
    public String getTeacherObj() {
        return teacherObj;
    }
    public void setTeacherObj(String teacherObj) {
        this.teacherObj = teacherObj;
    }

    /*�ظ�ʱ��*/
    private String replyTime;
    public String getReplyTime() {
        return replyTime;
    }
    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    /*�ظ���־*/
    private int replyFlag;
    public int getReplyFlag() {
        return replyFlag;
    }
    public void setReplyFlag(int replyFlag) {
        this.replyFlag = replyFlag;
    }

}