package com.newth.scorehelper.bean.Inner;

/**
 * Created by Mr.chen on 2018/3/18.
 */

public class User {
    public static User user;

    private User() {
    }

    public static User getInstance() {
        if (user == null) {
            synchronized (User.class) {
                if (user == null) {
                    user = new User();
                }
            }
        }
        return user;
    }
    private String userName;
    private Long userStuID;
    private String userPassword;
    private String objID;
    private String joinTeamID;
    private boolean isLeader;
    private boolean isTeacher;

    public boolean isTeacher() {
        return isTeacher;
    }

    public void setTeacher(boolean teacher) {
        isTeacher = teacher;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }

    public String getJoinTeamID() {
        return joinTeamID;
    }

    public void setJoinTeamID(String joinTeamID) {
        this.joinTeamID = joinTeamID;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        User.user = user;
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

    public String getObjID() {
        return objID;
    }

    public void setObjID(String objID) {
        this.objID = objID;
    }

    @Override
    public String toString() {
        return "login name:"+userName+" stuID: "+userStuID+" isleader:"+isLeader;
    }
}
