package com.newth.scorehelper.bean;

import cn.bmob.v3.BmobObject;

/**
 *
 * @author Mr.chen
 * @date 2018/3/17
 */

public class UserBeanDB extends BmobObject {
    private String userName;
    private Long userStuID;
    private String userPassword;
    private boolean isLeader;
    private boolean isTeacher;
    private String joinTeamID;


    public String getJoinTeamID() {
        return joinTeamID;
    }

    public void setJoinTeamID(String joinTeamID) {
        this.joinTeamID = joinTeamID;
    }

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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }

    public boolean isTeacher() {
        return isTeacher;
    }

    public void setTeacher(boolean teacher) {
        isTeacher = teacher;
    }
}
