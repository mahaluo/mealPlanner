package com.example.CalPro.Utils.HelperClasses;


public class KitchenHelper {
    private static final String TAG = "KitchenHelper";
    protected Double carbs, protein, fat, calories, price;
    protected String name;
    protected Meal.Macros macros;

    public KitchenHelper() {
    }

    public KitchenHelper(Double carbs, Double protein, Double fat, Double calories, Double price) {
        this.carbs = carbs;
        this.protein = protein;
        this.fat = fat;
        this.calories = calories;
        this.price = price;
    }

    public KitchenHelper(String name, Macros macros) {
        this.name = name;
        this.macros = macros;
    }

    public static class Ingredient extends KitchenHelper {
        public Ingredient() {
        }
        public Ingredient(Double carbs, Double protein, Double fat, Double calories, Double price) {
            super(carbs, protein, fat, calories, price);
        }
    }

    public static class Meal extends KitchenHelper {
        public Meal() {
        }
        public Meal(String name, Macros macros) {
            super(name, macros);
        }
    }

    public static class Macros extends KitchenHelper {
        public Macros() {
        }
        public Macros(Double carbs, Double protein, Double fat, Double calories, Double price) {
            super(carbs, protein, fat, calories, price);
        }
    }

    //region getters & setters

    public Meal.Macros getMacros() {
        return macros;
    }

    public void setMacros(Meal.Macros macros) {
        this.macros = macros;
    }

    public Double getCarbs() {
        return carbs;
    }

    public Double getProtein() {
        return protein;
    }

    public Double getFat() {
        return fat;
    }

    public Double getCalories() {
        return calories;
    }

    public Double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void setCarbs(Double carbs) {
        this.carbs = carbs;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }
    //endregion
}

