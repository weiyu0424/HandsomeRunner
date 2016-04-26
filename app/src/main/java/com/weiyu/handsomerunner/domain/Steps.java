package com.weiyu.handsomerunner.domain;

/**
 * Created by Sam on 4/24/2016.
 */
public class Steps {

    private String userName = null;
    private int steps;
    private String updateTime = null;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Steps{" +
                "userName='" + userName + '\'' +
                ", steps=" + steps +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
