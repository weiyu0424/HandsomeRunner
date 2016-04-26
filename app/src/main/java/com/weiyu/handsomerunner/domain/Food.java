package com.weiyu.handsomerunner.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sam on 4/17/2016.
 */
public class Food implements Parcelable {
    private String foodId = null;
    private String foodName = null;
    private double serving;
    private double fat;
    private double calories;
    private String unit = null;


    public Food(){}

    protected Food(Parcel in) {
        foodId = in.readString();
        foodName = in.readString();
        serving = in.readDouble();
        fat = in.readDouble();
        calories = in.readDouble();
        unit = in.readString();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(foodId);
        dest.writeString(foodName);
        dest.writeDouble(serving);
        dest.writeDouble(fat);
        dest.writeDouble(calories);
        dest.writeString(unit);
    }
}
