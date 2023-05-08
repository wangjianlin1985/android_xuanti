package com.mobileserver.domain;

public class GuestBook {
    /*记录id*/
    private int guestBookId;
    public int getGuestBookId() {
        return guestBookId;
    }
    public void setGuestBookId(int guestBookId) {
        this.guestBookId = guestBookId;
    }

    /*标题*/
    private String qustion;
    public String getQustion() {
        return qustion;
    }
    public void setQustion(String qustion) {
        this.qustion = qustion;
    }

    /*提问学生*/
    private String student;
    public String getStudent() {
        return student;
    }
    public void setStudent(String student) {
        this.student = student;
    }

    /*提问时间*/
    private String questionTime;
    public String getQuestionTime() {
        return questionTime;
    }
    public void setQuestionTime(String questionTime) {
        this.questionTime = questionTime;
    }

    /*老师回复*/
    private String reply;
    public String getReply() {
        return reply;
    }
    public void setReply(String reply) {
        this.reply = reply;
    }

    /*解答老师*/
    private String teacherObj;
    public String getTeacherObj() {
        return teacherObj;
    }
    public void setTeacherObj(String teacherObj) {
        this.teacherObj = teacherObj;
    }

    /*回复时间*/
    private String replyTime;
    public String getReplyTime() {
        return replyTime;
    }
    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    /*回复标志*/
    private int replyFlag;
    public int getReplyFlag() {
        return replyFlag;
    }
    public void setReplyFlag(int replyFlag) {
        this.replyFlag = replyFlag;
    }

}