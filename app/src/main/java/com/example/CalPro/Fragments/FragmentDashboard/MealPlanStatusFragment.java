package com.example.CalPro.Fragments.FragmentDashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.CalPro.R;
import com.example.CalPro.Utils.HelperClasses.FirebaseHelper;
import com.example.CalPro.Utils.Interfaces.FirebaseInterface;
import com.example.CalPro.Utils.HelperClasses.MealPlanHelper;

public class MealPlanStatusFragment extends Fragment implements FirebaseInterface {
    private static final String TAG = "MealPlanStatusFrag";
    private int page;
    private TextView mealPlanStatusCalories, mealPlanStatusCarbs, mealPlanStatusProtein, mealPlanStatusFat, mealPlanStatusCost;
    private TextView mealPlanStatusMissingCalories, mealPlanStatusMissingCarbs, mealPlanStatusMissingProtein, mealPlanStatusMissingFat;
    private String activeCaloriesValue, activeCarbsValue, activeProteinValue, activeFatValue, costValue;
    private String dailyCaloriesValue, dailyCarbsValue, dailyProteinValue, dailyFatValue;

    // newInstance constructor for creating fragment with arguments
    public static MealPlanStatusFragment newInstance(int page) {
        MealPlanStatusFragment fragmentFirst = new MealPlanStatusFragment();
        Bundle args = new Bundle();
        args.putInt("1", page);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: meal plan status fragment created");
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("1", 0);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_plan_status , container, false);
        findTextViews(view);
        readFirebase();
        return view;
    }

    @Override
    public void readFirebase() {
        new FirebaseHelper().readMealPlanStatus(new FirebaseHelper.MealPlanStatusDataStatus() {

            @Override
            public void MealPlanStatuslsLoaded(MealPlanHelper.MealPlanStatus.DailyValues dailyValues, MealPlanHelper.MealPlanStatus.ActiveValues activeValues) {
                activeCaloriesValue = "Calories: " + activeValues.getActiveCalories().toString() + " kcal";
                mealPlanStatusCalories.setText(activeCaloriesValue);

                activeCarbsValue = "Carbs: " + activeValues.getActiveCarbs().toString() + " grams";
                mealPlanStatusCarbs.setText(activeCarbsValue);

                activeProteinValue = "Protein: " + activeValues.getActiveProtein().toString() + " grams";
                mealPlanStatusProtein.setText(activeProteinValue);

                activeFatValue = "Fat: " + activeValues.getActiveFat().toString() + " grams";
                mealPlanStatusFat.setText(activeFatValue);

                costValue = "Cost: " + activeValues.getCost().toString() + " $AUD";
                mealPlanStatusCost.setText(costValue);

                dailyCaloriesValue = " out of: " + dailyValues.getDailyCalories() + " kcal";
                mealPlanStatusMissingCalories.setText(dailyCaloriesValue);

                dailyCarbsValue = " out of: " + dailyValues.getDailyCarbs() + " grams";
                mealPlanStatusMissingCarbs.setText(dailyCarbsValue);

                dailyProteinValue = " out of: " + dailyValues.getDailyProtein() + " grams";
                mealPlanStatusMissingProtein.setText(dailyProteinValue);

                dailyFatValue = " out of: " + dailyValues.getDailyFat() + " grams";
                mealPlanStatusMissingFat.setText(dailyFatValue);
            }

            @Override
            public void MealPlanStatusIsInsertedFromDailyValues() {

            }

            @Override
            public void MealPlanStatusIsInsertedFromActiveValues() {

            }

            @Override
            public void MealPlanStatusIsUpdated() {

            }

            @Override
            public void MealPlanStatusIsDeleted() {

            }
        });
    }

    private void findTextViews(View view) {
        mealPlanStatusCalories = view.findViewById(R.id.ingredientCaloriesTextView);
        mealPlanStatusCarbs =  view.findViewById(R.id.carbsTextView);
        mealPlanStatusProtein =  view.findViewById(R.id.proteinTextView);
        mealPlanStatusFat =  view.findViewById(R.id.fatTextView);
        mealPlanStatusCost =  view.findViewById(R.id.costTextView);
        mealPlanStatusMissingCalories = view.findViewById(R.id.missingCaloriesTextView);
        mealPlanStatusMissingCarbs = view.findViewById(R.id.missingCarbsTextView);
        mealPlanStatusMissingProtein = view.findViewById(R.id.missingProteinTextView);
        mealPlanStatusMissingFat = view.findViewById(R.id.missingFatTextView);
    }
}
