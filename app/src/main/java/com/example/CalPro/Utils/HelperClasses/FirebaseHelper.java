package com.example.CalPro.Utils.HelperClasses;

import android.util.Log;

import androidx.annotation.NonNull;

import com.github.mikephil.charting.data.Entry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.CalPro.Utils.CONSTANTS.MEAL_INGREDIENT_REFERENCE;
import static com.example.CalPro.Utils.CONSTANTS.MEAL_MACROS_REFERENCE;

public class FirebaseHelper {
    private static final String TAG = "FirebaseHelper";
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceIngredients, mReferenceStoredMeals, mReferenceActiveMeals;
    private DatabaseReference mReferenceDailyDonut, mReferenceDailyMacros, mReferenceMealPlanStatus;
    private DatabaseReference mReferenceWeightChart;
    private List<KitchenHelper.Ingredient> ingredients = new ArrayList<>();
    private List<KitchenHelper.Meal> storedMeals = new ArrayList<>();
    private List<KitchenHelper.Meal> activeMeals = new ArrayList<>();
    private KitchenHelper.Meal ingredientMeal = new KitchenHelper.Meal();
    private List<String> mealKeys = new ArrayList<>();
    private List<Entry> entryList = new ArrayList<>();

    public FirebaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceIngredients = mDatabase.getReference("Ingredient");
        mReferenceStoredMeals = mDatabase.getReference("Stored Meals");
        mReferenceActiveMeals = mDatabase.getReference("Active Meals");
        mReferenceDailyDonut = mDatabase.getReference("Daily Donut");
        mReferenceDailyMacros = mDatabase.getReference("Daily Macros");
        mReferenceMealPlanStatus = mDatabase.getReference("Meal Plan Status");
        mReferenceWeightChart = mDatabase.getReference("Weight Chart");
    }

    public interface IngredientDataStatus {
        void IngredientIsLoaded(List<KitchenHelper.Ingredient> ingredients, List<String> keys);
        void IngredientIsInserted();
        void IngredientIsUpdated();
        void IngredientIsDeleted();
    }
    public interface StoredMealsDataStatus {
        void StoredMealsIsLoaded(List<KitchenHelper.Meal> storedMeals, List<String > keys);
        void StoredMealsIsInserted();
        void StoredMealsIsUpdated();
        void StoredMealsIsDeleted();
    }
    public interface ActiveMealsDataStatus {
        void ActiveMealsIsLoaded(List<KitchenHelper.Meal> activeMeals, List<String > keys);
        void ActiveMealsIsInserted();
        void ActiveMealsIsUpdated();
        void ActiveMealsIsDeleted();
    }
    public interface DailyDonutDataStatus{
        void DailyDonutIsLoaded(MealPlanHelper.DonutChart donutChart);
        void DailyDonutIsInserted();
        void DailyDonutIsUpdated(MealPlanHelper.DonutChart donutChart);
        void DailyDonutIsDeleted();
    }
    public interface DailyMacrosDataStatus{
        void DailyMacrosIsLoaded(MealPlanHelper.DailyMacrosValues dailyMacrosValues);
        void DailyMacrosIsInserted();
        void DailyMacrosIsUpdated();
        void DailyMacrosIsDeleted();
    }
    public interface MealPlanStatusDataStatus{
        void MealPlanStatuslsLoaded(MealPlanHelper.MealPlanStatus.DailyValues dailyValues, MealPlanHelper.MealPlanStatus.ActiveValues activeValues);
        void MealPlanStatusIsInsertedFromDailyValues();
        void MealPlanStatusIsInsertedFromActiveValues();
        void MealPlanStatusIsUpdated();
        void MealPlanStatusIsDeleted();
    }
    public interface WeightChartDataStatus{
        void WeightChartIsLoaded(List<Entry> entries);
        void WeightChartIsInserted();
        void WeightChartIsUpdated();
        void WeightChartIsDeleted();
    }

    public void readIngredients(final IngredientDataStatus ingredientDataStatus){
        mReferenceIngredients.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ingredients.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Log.d(TAG, "onDataChange: " + keyNode.getValue());
                    KitchenHelper.Ingredient ingredient = keyNode.getValue(KitchenHelper.Ingredient.class);
                    ingredients.add(ingredient);
                }
                ingredientDataStatus.IngredientIsLoaded(ingredients, keys);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readStoredMeals(final StoredMealsDataStatus storedMealsDataStatus){
        mReferenceStoredMeals.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                storedMeals.clear();
                mealKeys.clear();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    mealKeys.add(keyNode.getKey());
                    Log.d(TAG, "onDataChange: reading meal: " + keyNode.getKey() + " values: " + keyNode.getValue());
                    KitchenHelper.Meal meal = keyNode.getValue(KitchenHelper.Meal.class);
                    storedMeals.add(meal);
                }
                storedMealsDataStatus.StoredMealsIsLoaded(storedMeals, mealKeys);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readActiveMeals(final ActiveMealsDataStatus activeMealsDataStatus){
        mReferenceActiveMeals.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                activeMeals.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Log.d(TAG, "onDataChange: reading meal: " + keyNode.getKey() + " values: " + keyNode.getValue());
                    KitchenHelper.Meal meal = keyNode.getValue(KitchenHelper.Meal.class);
                    activeMeals.add(meal);
                }
                activeMealsDataStatus.ActiveMealsIsLoaded(activeMeals, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readDailyDonutValues(final DailyDonutDataStatus dailyDonutDataStatus) {
        mReferenceDailyDonut.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MealPlanHelper.DonutChart donutChart = dataSnapshot.getValue(MealPlanHelper.DonutChart.class);
                dailyDonutDataStatus.DailyDonutIsLoaded(donutChart);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readDailyMacrosValues(final DailyMacrosDataStatus dailyMacrosDataStatus){
        mReferenceDailyMacros.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MealPlanHelper.DailyMacrosValues dailyMacrosValues = dataSnapshot.getValue(MealPlanHelper.DailyMacrosValues.class);
                dailyMacrosDataStatus.DailyMacrosIsLoaded(dailyMacrosValues);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readMealPlanStatus(final MealPlanStatusDataStatus mealPlanStatusDataStatus){
      mReferenceMealPlanStatus.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              MealPlanHelper.MealPlanStatus.DailyValues dailyValues = dataSnapshot.child("Daily Values").getValue(MealPlanHelper.MealPlanStatus.DailyValues.class);
              MealPlanHelper.MealPlanStatus.ActiveValues activeValues = dataSnapshot.child("Active Values").getValue(MealPlanHelper.MealPlanStatus.ActiveValues.class);
              mealPlanStatusDataStatus.MealPlanStatuslsLoaded(dailyValues, activeValues);
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });
    }

    public void readWeightProgressChart(final WeightChartDataStatus weightChartDataStatus, final Integer year, final String month) {
        DatabaseReference readCurrentMonth = mReferenceWeightChart.child(year.toString()).child(month);
        Log.d(TAG, "readWeightProgressChart: reading from year: " + year.toString() + " month: " + month);
        readCurrentMonth.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                entryList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot node: dataSnapshot.getChildren()) {
                        entryList.add(new Entry(Integer.valueOf(node.getKey().replaceAll("\\D+","")), node.child("weight").getValue(float.class)));
                    }
                }
                weightChartDataStatus.WeightChartIsLoaded(entryList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void deleteActiveMeals(final ActiveMealsDataStatus activeMealsDataStatus, final String mealName) {
        Log.d(TAG, "onDataChange: Removing " + mealName + " from active meals list");
        DatabaseReference activeMeal = mReferenceActiveMeals;
        activeMeal.child(mealName).removeValue();
        activeMealsDataStatus.ActiveMealsIsDeleted();
    }

    public void deleteWeightChartEntry(final WeightChartDataStatus weightChartDataStatus, final Integer year, final String month, final Integer entryNumber) {
        Log.d(TAG, "deleteWeightChartEntry: Removing entry from " + month + " " + year + " entry number: " + entryNumber + " from weight chart");
        DatabaseReference chartEntry = mReferenceWeightChart.child(year.toString()).child(month).child("Day " + entryNumber.toString());
        chartEntry.removeValue();
        weightChartDataStatus.WeightChartIsDeleted();
    }

    public void insertActiveMeals(final ActiveMealsDataStatus activeMealsDataStatus, final KitchenHelper.Meal updatedMeal) {
        Log.d(TAG, "insertActiveMeals: Activated meal " + updatedMeal.getName());
        DatabaseReference activeMeal = mReferenceActiveMeals.child(updatedMeal.name);
        activeMeal.setValue(updatedMeal);
        activeMealsDataStatus.ActiveMealsIsInserted();
    }

    public void insertWeightChartEntry(final WeightChartDataStatus weightChartDataStatus, final Integer year, final String month, final Integer day, final Double weight) {
        Log.d(TAG, "insertWeightChartEntry: Adding weight " + weight + " to entry: " + day + " " + month + " " + year + " weight: " + weight);
        DatabaseReference weightEntry = mReferenceWeightChart.child(year.toString()).child(month).child("Day " + day);
        weightEntry.child("weight").setValue(weight);
        weightChartDataStatus.WeightChartIsInserted();
    }

    public void insertStoredMeals(final StoredMealsDataStatus storedMealsDataStatus, KitchenHelper.Meal newMeal, List<KitchenHelper.Ingredient> ingredientList){
        Log.d(TAG, "insertStoredMeals: Adding meal " + newMeal.getName());
        DatabaseReference mealEntry = mReferenceStoredMeals.child(newMeal.getName());
        for(KitchenHelper.Ingredient ingredient : ingredientList) {
            Log.d(TAG, "insertStoredMeals: Adding ingredient " + ingredient.getName());
            mealEntry.child(MEAL_INGREDIENT_REFERENCE).child(ingredient.getName()).setValue(ingredient);
        }

        mealEntry.child(MEAL_MACROS_REFERENCE).setValue(newMeal.getMacros());

        storedMealsDataStatus.StoredMealsIsInserted();
    }

    public void insertIngredients(final IngredientDataStatus ingredientDataStatus, final KitchenHelper.Ingredient ingredient) {
        Log.d(TAG, "insertIngredients: inserting " + ingredient.getName());
        DatabaseReference ingredientEntry = mReferenceIngredients.child(ingredient.getName());
        ingredientEntry.setValue(ingredient);
        ingredientDataStatus.IngredientIsInserted();
    }

    public void updateDailyMacros(final DailyMacrosDataStatus dailyMacrosDataStatus, final MealPlanHelper.DailyMacrosValues dailyMacrosValues) {
        Log.d(TAG, "updateDailyMacros: Updating daily macros");
        DatabaseReference dailyMacros = mReferenceDailyMacros;
        dailyMacros.setValue(dailyMacrosValues);
        dailyMacrosDataStatus.DailyMacrosIsUpdated();
    }

    public void updateMealPlanStatusFromDailyMacros(final MealPlanStatusDataStatus mealPlanStatusDataStatus, final MealPlanHelper.MealPlanStatus.DailyValues dailyValues){
        Log.d(TAG, "updateMealPlanStatusFromDailyMacros: inserted new values");
        DatabaseReference mealPlanStatus = mReferenceMealPlanStatus.child("Daily Values");
        mealPlanStatus.setValue(dailyValues);
        mealPlanStatusDataStatus.MealPlanStatusIsInsertedFromDailyValues();
    }

    public void updateMealPlanStatusFromActiveMeals(final MealPlanStatusDataStatus mealPlanStatusDataStatus, final MealPlanHelper.MealPlanStatus.ActiveValues activeValues){
        Log.d(TAG, "updateMealPlanStatusFromActiveMeals: inserted new values");
        DatabaseReference mealPlanStatus = mReferenceMealPlanStatus.child("Active Values");
        mealPlanStatus.setValue(activeValues);
        mealPlanStatusDataStatus.MealPlanStatusIsInsertedFromActiveValues();
    }

    public void updateDailyDonutChart(final DailyDonutDataStatus dailyDonutDataStatus, final MealPlanHelper.DonutChart donutChart){
        Log.d(TAG, "updateDailyDonutChart: updating donut chart");
        DatabaseReference dailyDonut = mReferenceDailyDonut;
        dailyDonut.setValue(donutChart);
        dailyDonutDataStatus.DailyDonutIsUpdated(donutChart);
    }

    public void updateWeightChart(final WeightChartDataStatus weightChartDataStatus, final Integer year, final String month) {
        Log.d(TAG, "updateWeightChart: updating with new month: " + month);
        DatabaseReference weightChart = mReferenceWeightChart.child(year.toString()).child(month);
        weightChart.setValue("0", 0);
        weightChartDataStatus.WeightChartIsUpdated();
    }
}
