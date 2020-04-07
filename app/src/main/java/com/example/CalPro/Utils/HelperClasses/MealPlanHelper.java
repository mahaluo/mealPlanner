package com.example.CalPro.Utils.HelperClasses;

public class MealPlanHelper {
    private static final String TAG = "MealPlanHelper";
    private Double carbs, protein, fat, calories, intakeLevel, bodyWeight;
    private Double activeCalories, activeCarbs, activeProtein, activeFat;
    private Double dailyCalories, dailyCarbs, dailyProtein, dailyFat;
    private Double cost;

    public MealPlanHelper() { }

    public static class DailyMacrosValues extends MealPlanHelper {
        public DailyMacrosValues() { }
    }
    public static class MealPlanStatus extends MealPlanHelper{
        public MealPlanStatus() { }
        public static class ActiveValues extends MealPlanStatus {
            public ActiveValues() { }
        }
        public static class DailyValues extends MealPlanStatus {
            public DailyValues() { }
        }
    }
    public static class DonutChart extends MealPlanHelper {
        public DonutChart() { }
    }

    //region getters & setters
    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getCarbs() {
        return carbs;
    }

    public void setCarbs(Double carbs) {
        this.carbs = carbs;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Double getIntakeLevel() {
        return intakeLevel;
    }

    public void setIntakeLevel(Double intakeLevel) {
        this.intakeLevel = intakeLevel;
    }

    public Double getBodyWeight() {
        return bodyWeight;
    }

    public void setBodyWeight(Double bodyWeight) {
        this.bodyWeight = bodyWeight;
    }

    public Double getActiveCalories() {
        return activeCalories;
    }

    public void setActiveCalories(Double activeCalories) {
        this.activeCalories = activeCalories;
    }

    public Double getActiveCarbs() {
        return activeCarbs;
    }

    public void setActiveCarbs(Double activeCarbs) {
        this.activeCarbs = activeCarbs;
    }

    public Double getActiveProtein() {
        return activeProtein;
    }

    public void setActiveProtein(Double activeProtein) {
        this.activeProtein = activeProtein;
    }

    public Double getActiveFat() {
        return activeFat;
    }

    public void setActiveFat(Double activeFat) {
        this.activeFat = activeFat;
    }

    public Double getDailyCalories() {
        return dailyCalories;
    }

    public void setDailyCalories(Double dailyCalories) {
        this.dailyCalories = dailyCalories;
    }

    public Double getDailyCarbs() {
        return dailyCarbs;
    }

    public void setDailyCarbs(Double dailyCarbs) {
        this.dailyCarbs = dailyCarbs;
    }

    public Double getDailyProtein() {
        return dailyProtein;
    }

    public void setDailyProtein(Double dailyProtein) {
        this.dailyProtein = dailyProtein;
    }

    public Double getDailyFat() {
        return dailyFat;
    }

    public void setDailyFat(Double dailyFat) {
        this.dailyFat = dailyFat;
    }
    //endregion
}
