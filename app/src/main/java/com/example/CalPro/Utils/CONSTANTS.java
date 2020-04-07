package com.example.CalPro.Utils;

public class CONSTANTS {

    public static final String ERROR = "Unexpected";

    public static final String ERROR_FRAGMENT = "ErrorFragment";
    public static final String NEW_WEIGHT_ENTRY = "NewWeightEntry";
    public static final String NEW_MEAL_FRAGMENT = "NewMealFragment";
    public static final String NEW_INGREDIENT_FRAGMENT = "CreateIngredientFragment";
    public static final String STORED_MEALS_FRAGMENT = "StoredMealsFragment";
    public static final String STORED_INGREDIENTS_FRAGMENT = "StoredIngredientsFragment";
    public static final String EDIT_STORED_MEALS_FRAGMENT = "EditStoredMealsFragment";
    public static final String ADD_INGREDIENT_TO_MEAL_FRAGMENT = "AddIngredientToMealRecyclerFragment";
    public static final String INGREDIENT_AMOUNT_FRAGMENT = "IngredientAmountFragment";
    public static final String CREATE_MEAL_FRAGMENT = "CreateMealFragment";


    public static final int PAGER_ADAPTER_TOP_NUM_FRAGMENTS = 5;
    public static final int PAGER_ADAPTER_BOTTOM_DASHBOARD_NUM_FRAGMENTS = 4;
    public static final int PAGER_ADAPTER_BOTTOM_PROGRESS_NUM_FRAGMENTS = 3;

    public static final String EMPTY_DONUT = "Your macros will be shown here";
    public static final String EMPTY_WEIGHT_CHART = "Your weight entries will be shown here";

    public static final String JANUARY = "January";
    public static final String FEBRUARY = "February";
    public static final String MARCH = "March";
    public static final String APRIL = "April";
    public static final String MAY = "May";
    public static final String JUNE = "June";
    public static final String JULY = "July";
    public static final String AUGUST = "August";
    public static final String SEPTEMBER = "September";
    public static final String OCTOBER = "October";
    public static final String NOVEMBER = "November";
    public static final String DECEMBER = "December";


    public static String getMonthName(int monthNumber){
        switch(monthNumber) {
            case 0: return JANUARY;
            case 1: return FEBRUARY;
            case 2: return MARCH;
            case 3: return APRIL;
            case 4: return MAY;
            case 5: return JUNE;
            case 6: return JULY;
            case 7: return AUGUST;
            case 8: return SEPTEMBER;
            case 9: return OCTOBER;
            case 10: return NOVEMBER;
            case 11: return DECEMBER;
            default: return ERROR;
        }
    }


    public static final String MEAL_INGREDIENT_REFERENCE = "mealIngredient";
    public static final String MEAL_MACROS_REFERENCE = "macros";

}
