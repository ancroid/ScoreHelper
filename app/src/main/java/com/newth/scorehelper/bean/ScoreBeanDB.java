package com.newth.scorehelper.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Mr.chen on 2018/3/17.
 */

public class ScoreBeanDB extends BmobObject {
    private String userName;
    private Long userStuID;
    private Integer answerScore;
    private Integer noteScore;
    private Integer codeScore;
    private Integer week;

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

    public Integer getAnswerScore() {
        return answerScore;
    }

    public void setAnswerScore(Integer answerScore) {
        this.answerScore = answerScore;
    }

    public Integer getNoteScore() {
        return noteScore;
    }

    public void setNoteScore(Integer noteScore) {
        this.noteScore = noteScore;
    }

    public Integer getCodeScore() {
        return codeScore;
    }

    public void setCodeScore(Integer codeScore) {
        this.codeScore = codeScore;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }
}
