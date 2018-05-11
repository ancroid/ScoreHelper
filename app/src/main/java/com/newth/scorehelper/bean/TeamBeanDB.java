package com.newth.scorehelper.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Mr.chen on 2018/3/17.
 */

public class TeamBeanDB extends BmobObject {

    private String teamName;
    private String teamLeaderStuID;
    private List<String> teamMembName;
    private List<String> teamMembStuID;
    private Integer teamScore;
    private transient String inviteNum;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamLeaderStuID() {
        return teamLeaderStuID;
    }

    public void setTeamLeaderStuID(String teamLeaderStuID) {
        this.teamLeaderStuID = teamLeaderStuID;
    }

    public List<String> getTeamMembName() {
        return teamMembName;
    }

    public void setTeamMembName(List<String> teamMembName) {
        this.teamMembName = teamMembName;
    }

    public List<String> getTeamMembStuID() {
        return teamMembStuID;
    }

    public void setTeamMembStuID(List<String> teamMembStuID) {
        this.teamMembStuID = teamMembStuID;
    }

    public String getInviteNum() {
        return inviteNum;
    }

    public void setInviteNum(String inviteNum) {
        this.inviteNum = inviteNum;
    }

    public Integer getTeamScore() {
        return teamScore;
    }

    public void setTeamScore(Integer teamScore) {
        this.teamScore = teamScore;
    }

}
