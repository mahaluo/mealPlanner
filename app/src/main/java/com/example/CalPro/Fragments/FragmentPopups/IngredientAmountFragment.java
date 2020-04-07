package com.example.CalPro.Fragments.FragmentPopups;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.CalPro.Activities.MainActivity;
import com.example.CalPro.R;
import com.example.CalPro.Utils.HelperClasses.FirebaseHelper;
import com.example.CalPro.Utils.HelperClasses.KitchenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xw.repo.BubbleSeekBar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.example.CalPro.Utils.CONSTANTS.INGREDIENT_AMOUNT_FRAGMENT;

public class IngredientAmountFragment extends Fragment {
    private static final String TAG = "IngredientAmountFrag";
    private TextView ingredientName;
    private FloatingActionButton addIngredientButton, viewMealButton, storeMealButton;
    private KitchenHelper.Meal newMeal;
    private KitchenHelper.Ingredient currentIngredient = new KitchenHelper.Ingredient();
    private static List<KitchenHelper.Ingredient> ingredientList = new ArrayList<>();
    private KitchenHelper.Ingredient mealIngredient;
    private KitchenHelper.Meal.Macros macrosValues;
    private BubbleSeekBar ingredientAmountSeekBar;
    private TextView calorieCounter, carbsCounter, proteinCounter, fatCounter, priceCounter;
    private Double price, carbs, protein, fat, calories;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_amount, container, false);
        Log.d(TAG, "onCreateView: view created");
        ingredientName = view.findViewById(R.id.ingredientNameTextView);
        addIngredientButton = view.findViewById(R.id.addIngredientButton);
        viewMealButton = view.findViewById(R.id.viewMealButton);
        storeMealButton = view.findViewById(R.id.storeMealButton);
        ingredientAmountSeekBar = view.findViewById(R.id.ingredientAmountSeekBar);

        calorieCounter = view.findViewById(R.id.ingredientCaloriesCounterTextView);
        carbsCounter = view.findViewById(R.id.ingredientCarbsCounterTextView);
        proteinCounter = view.findViewById(R.id.ingredientProteinCounterTextView);
        fatCounter = view.findViewById(R.id.ingredientFatCounterTextView);
        priceCounter = view.findViewById(R.id.ingredientPriceCounterTextView);

        newMeal = new KitchenHelper.Meal();
        mealIngredient = new KitchenHelper.Ingredient();
        newMeal.setName(((MainActivity)getActivity()).getNewMealName());

        currentIngredient = ((MainActivity)getActivity()).getCurrentIngredient();
        ingredientName.setText(currentIngredient.getName());
        priceCounter.setText(String.valueOf(currentIngredient.getPrice()));
        carbsCounter.setText(String.valueOf(currentIngredient.getCarbs()));
        proteinCounter.setText(String.valueOf(currentIngredient.getProtein()));
        fatCounter.setText(String.valueOf(currentIngredient.getFat()));
        calorieCounter.setText(String.valueOf(currentIngredient.getCalories()));


        ingredientAmountSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                if (fromUser){

                    price = currentIngredient.getPrice();
                    price = price * progress;
                    BigDecimal priceRefined = new BigDecimal(String.valueOf(price)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    priceCounter.setText(String.valueOf(priceRefined));

                    carbs = currentIngredient.getCarbs();
                    carbs = carbs * progress;
                    BigDecimal carbsRefined = new BigDecimal(String.valueOf(carbs)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    carbsCounter.setText(String.valueOf(carbsRefined));

                    protein = currentIngredient.getProtein();
                    protein = protein * progress;
                    BigDecimal proteinRefined = new BigDecimal(String.valueOf(protein)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    proteinCounter.setText(String.valueOf(proteinRefined));

                    fat = currentIngredient.getFat();
                    fat = fat * progress;
                    BigDecimal fatRefined = new BigDecimal(String.valueOf(fat)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    fatCounter.setText(String.valueOf(fatRefined));

                    calories = currentIngredient.getCalories();
                    calories = calories * progress;
                    BigDecimal caloriesRefined = new BigDecimal(String.valueOf(calories)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    calorieCounter.setText(String.valueOf(caloriesRefined));
                }
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {


            }
        });

        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredientList.add(setIngredientValues());
                ((MainActivity)getActivity()).toastMessage("ingredient added to " + newMeal.getName());
            }
        });

        viewMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MainActivity)getActivity()).toastMessage("Not implemented yet");
            }
        });

        storeMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newMeal.setMacros(setMacrosValues(ingredientList));
                insertFirebase(newMeal, ingredientList);
                ((MainActivity)getActivity()).toastMessage(newMeal.getName() + " stored");
                ((MainActivity)getActivity()).clearFragmentContainer();
                ingredientList.clear();
            }
        });

        return view;
    }

    private KitchenHelper.Ingredient setIngredientValues() {

        mealIngredient.setPrice(Double.valueOf(priceCounter.getText().toString()));
        mealIngredient.setCarbs(Double.valueOf(carbsCounter.getText().toString()));
        mealIngredient.setFat(Double.valueOf(fatCounter.getText().toString()));
        mealIngredient.setCalories(Double.valueOf(calorieCounter.getText().toString()));
        mealIngredient.setProtein(Double.valueOf(proteinCounter.getText().toString()));
        mealIngredient.setName(ingredientName.getText().toString());

        return mealIngredient;
    }

    private KitchenHelper.Macros setMacrosValues(List<KitchenHelper.Ingredient> ingredientList){

        macrosValues = new KitchenHelper.Meal.Macros(0.0, 0.0, 0.0, 0.0, 0.0);

        for(KitchenHelper.Ingredient ingredient : ingredientList) {
            macrosValues.setPrice(macrosValues.getPrice() + ingredient.getPrice());
            macrosValues.setCarbs(macrosValues.getCarbs() + ingredient.getCarbs());
            macrosValues.setProtein(macrosValues.getProtein() + ingredient.getProtein());
            macrosValues.setFat(macrosValues.getFat() + ingredient.getFat());
            macrosValues.setCalories(macrosValues.getCalories() + ingredient.getCalories());
        }

        BigDecimal refinedCalories = new BigDecimal(String.valueOf(macrosValues.getCalories())).setScale(2, BigDecimal.ROUND_HALF_UP);
        macrosValues.setCalories(Double.parseDouble(refinedCalories.toString()));
        BigDecimal refinedCarbs = new BigDecimal(String.valueOf(macrosValues.getCarbs())).setScale(2, BigDecimal.ROUND_HALF_UP);
        macrosValues.setCarbs(Double.parseDouble(refinedCarbs.toString()));
        BigDecimal refinedProtein = new BigDecimal(String.valueOf(macrosValues.getProtein())).setScale(2, BigDecimal.ROUND_HALF_UP);
        macrosValues.setProtein(Double.parseDouble(refinedProtein.toString()));
        BigDecimal refinedFat = new BigDecimal(String.valueOf(macrosValues.getFat())).setScale(2, BigDecimal.ROUND_HALF_UP);
        macrosValues.setFat(Double.parseDouble(refinedFat.toString()));
        BigDecimal refinedPrice = new BigDecimal(String.valueOf(macrosValues.getPrice())).setScale(2, BigDecimal.ROUND_HALF_UP);
        macrosValues.setPrice(Double.parseDouble(refinedPrice.toString()));

        return macrosValues;
    }

    private void insertFirebase(KitchenHelper.Meal newMeal, List<KitchenHelper.Ingredient> ingredientList) {
        new FirebaseHelper().insertStoredMeals(new FirebaseHelper.StoredMealsDataStatus() {
            @Override
            public void StoredMealsIsLoaded(List<KitchenHelper.Meal> storedMeals, List<String> keys) {

            }

            @Override
            public void StoredMealsIsInserted() {
                Log.d(TAG, "StoredMealsIsInserted: stored new meal");
            }

            @Override
            public void StoredMealsIsUpdated() {

            }

            @Override
            public void StoredMealsIsDeleted() {

            }
        }, newMeal, ingredientList);
    }
}
