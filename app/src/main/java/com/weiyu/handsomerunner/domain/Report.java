package com.weiyu.handsomerunner.domain;

/**
 * Created by Sam on 4/27/2016.
 */
public class Report {
    private String userId = null;
    private double consumedCalories;
    private double burnedCalories;
    private double steps;
    private double goaledCalories;
    private double remainingCalories;
    private String updateTime = null;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getConsumedCalories() {
        return consumedCalories;
    }

    public void setConsumedCalories(double consumedCalories) {
        this.consumedCalories = consumedCalories;
    }

    public double getBurnedCalories() {
        return burnedCalories;
    }

    public void setBurnedCalories(double burnedCalories) {
        this.burnedCalories = burnedCalories;
    }

    public double getSteps() {
        return steps;
    }

    public void setSteps(double steps) {
        this.steps = steps;
    }

    public double getGoaledCalories() {
        return goaledCalories;
    }

    public void setGoaledCalories(double goaledCalories) {
        this.goaledCalories = goaledCalories;
    }

    public double getRemainingCalories() {
        return remainingCalories;
    }

    public void setRemainingCalories(double remainingCalories) {
        this.remainingCalories = remainingCalories;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Report{" +
                "userId='" + userId + '\'' +
                ", consumedCalories=" + consumedCalories +
                ", burnedCalories=" + burnedCalories +
                ", steps=" + steps +
                ", goaledCalories=" + goaledCalories +
                ", remainingCalories=" + remainingCalories +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
