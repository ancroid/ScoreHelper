package com.newth.scorehelper.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Mr.chen on 2018/3/17.
 */

public class TeamBeanDB extends BmobObject {

    private String teamName;
    private Long teamLeaderStuID;
    private List<String> teamMembName;
    private List<Long> teamMembStuID;
    private Integer teamScore;
    private transient String inviteNum;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Long getTeamLeaderStuID() {
        return teamLeaderStuID;
    }

    public void setTeamLeaderStuID(Long teamLeaderStuID) {
        this.teamLeaderStuID = teamLeaderStuID;
    }

    public List<String> getTeamMembName() {
        return teamMembName;
    }

    public void setTeamMembName(List<String> teamMembName) {
        this.teamMembName = teamMembName;
    }

    public List<Long> getTeamMembStuID() {
        return teamMembStuID;
    }

    public void setTeamMembStuID(List<Long> teamMembStuID) {
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
