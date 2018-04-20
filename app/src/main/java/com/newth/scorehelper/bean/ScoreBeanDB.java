package com.newth.scorehelper.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Mr.chen on 2018/3/17.
 */

public class ScoreBeanDB extends BmobObject {
    private String userName;
    private Long userStuID;
    private String answerScore;
    private String noteScore;
    private String codeScore;
    private String week;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserStuID() {
        return userStuID;
    }

    public void setUserStuID(Long userStuID) {
        this.userStuID = userStuID;
    }

    public String getAnswerScore() {
        return answerScore;
    }

    public void setAnswerScore(String answerScore) {
        this.answerScore = answerScore;
    }

    public String getNoteScore() {
        return noteScore;
    }

    public void setNoteScore(String noteScore) {
        this.noteScore = noteScore;
    }

    public String getCodeScore() {
        return codeScore;
    }

    public void setCodeScore(String codeScore) {
        this.codeScore = codeScore;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
