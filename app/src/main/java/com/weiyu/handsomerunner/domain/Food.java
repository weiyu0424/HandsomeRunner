package com.weiyu.handsomerunner.domain;

/**
 * Created by Sam on 4/17/2016.
 */
public class Food {
    private String foodId = null;
    private String foodName = null;
    private double serving;
    private double fat;
    private double calories;
    private String unit = null;

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getServing() {
        return serving;
    }

    public void setServing(double serving) {
        this.serving = serving;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Food{" +
                "foodId='" + foodId + '\'' +
                ", foodName='" + foodName + '\'' +
                ", serving=" + serving +
                ", fat=" + fat +
                ", calories=" + calories +
                ", unit='" + unit + '\'' +
                '}';
    }
}
