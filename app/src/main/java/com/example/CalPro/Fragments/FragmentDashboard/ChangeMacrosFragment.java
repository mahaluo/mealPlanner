package com.example.CalPro.Fragments.FragmentDashboard;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.CalPro.R;
import com.example.CalPro.Utils.HelperClasses.FirebaseHelper;
import com.example.CalPro.Utils.Interfaces.FirebaseInterface;
import com.example.CalPro.Utils.HelperClasses.MealPlanHelper;
import com.xw.repo.BubbleSeekBar;
import java.math.BigDecimal;

public class ChangeMacrosFragment extends Fragment implements FirebaseInterface {
    private static final String TAG = "ChangeMacrosFrag";
    private int page;
    private BubbleSeekBar intakeSeekBar, carbsSeekBar, proteinSeekBar, fatSeekBar;
    private MealPlanHelper.DailyMacrosValues dailyMacrosValues;
    private MealPlanHelper.DonutChart donutChart;
    private TextView calorieCounter;
    private EditText bodyWeight;
    private Double calories;
    private String calorieCounted;
    private int min = 5;
    private int max = 60;
    private int step = 1;
    private DashboardFragment dashboardFragment = new DashboardFragment();


    // newInstance constructor for creating fragment with arguments
    public static ChangeMacrosFragment newInstance(int page) {
        ChangeMacrosFragment fragmentFirst = new ChangeMacrosFragment();
        Bundle args = new Bundle();
        args.putInt("2", page);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: change macros fragment created");
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("2", 0);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_macros , container, false);

        readFirebase();
        dailyMacrosValues = new MealPlanHelper.DailyMacrosValues();
        donutChart = new MealPlanHelper.DonutChart();
        intakeSeekBar = view.findViewById(R.id.ingredientAmountSeekBar);
        carbsSeekBar = view.findViewById(R.id.carbsSeekBar);
        proteinSeekBar = view.findViewById(R.id.proteinSeekBar);
        fatSeekBar = view.findViewById(R.id.fatSeekBar);
        calorieCounter = view.findViewById(R.id.calorieCounterTextView);
        bodyWeight = view.findViewById(R.id.bodyWeightTextEdit);

        intakeSeekBar.getConfigBuilder().max((max - min) / step).build();
        intakeSeekBar.getConfigBuilder().min(min).build();
        intakeSeekBar.getConfigBuilder().sectionCount(7).build();

        carbsSeekBar.getConfigBuilder().sectionCount(5).build();
        proteinSeekBar.getConfigBuilder().sectionCount(5).build();
        fatSeekBar.getConfigBuilder().sectionCount(5).build();

        intakeSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                if (bodyWeight.getText().toString().isEmpty()){
                    bodyWeight.setError("Enter weight first!");
                } else {
                    bodyWeight.setError(null);
                    calories = Double.valueOf(bodyWeight.getText().toString());
                    calories = (calories * 2.2) * progress;
                    BigDecimal calorie = new BigDecimal(String.valueOf(calories)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    calorieCounted = calorie + " kcal";
                    calorieCounter.setText(calorieCounted);

                    dailyMacrosValues.setBodyWeight(Double.parseDouble(bodyWeight.getText().toString()));
                    dailyMacrosValues.setIntakeLevel((double) progress);
                    dailyMacrosValues.setCalories(Double.parseDouble(calorie.toString()));
                }
            }
            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
            }
            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
            }
        });
        carbsSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                if(fromUser){
                    proteinSeekBar.setProgress((100 - carbsSeekBar.getProgress()) - fatSeekBar.getProgress());
                    fatSeekBar.setProgress(100 - (carbsSeekBar.getProgress() + proteinSeekBar.getProgress()));
                    carbsSeekBar.setProgress(progress);
                    updateDailyMacros(carbsSeekBar.getProgress(), proteinSeekBar.getProgress(), fatSeekBar.getProgress());
                }
            }
            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
            }
            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });
        proteinSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                if (fromUser){
                    carbsSeekBar.setProgress((100 - progress) - fatSeekBar.getProgress());
                    fatSeekBar.setProgress(100 - (progress + carbsSeekBar.getProgress()));
                    proteinSeekBar.setProgress(progress);
                    updateDailyMacros(carbsSeekBar.getProgress(), proteinSeekBar.getProgress(), fatSeekBar.getProgress());
                }
            }
            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }
            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });
        fatSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                if (fromUser){
                    proteinSeekBar.setProgress((100 - progress) - carbsSeekBar.getProgress());
                    carbsSeekBar.setProgress(100 - (progress + proteinSeekBar.getProgress()));
                    fatSeekBar.setProgress(progress);
                    updateDailyMacros(carbsSeekBar.getProgress(), proteinSeekBar.getProgress(), fatSeekBar.getProgress());
                }
            }
            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }
            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });

        return view;
    }

    private void updateDailyMacros(int carbs, int protein, int fat) {

        dailyMacrosValues.setCarbs((double)carbs);
        dailyMacrosValues.setProtein((double)protein);
        dailyMacrosValues.setFat((double)fat);
        updateFirebase(dailyMacrosValues);
    }

    @Override
    public void readFirebase() {
        new FirebaseHelper().readDailyMacrosValues(new FirebaseHelper.DailyMacrosDataStatus() {
            @Override
            public void DailyMacrosIsLoaded(MealPlanHelper.DailyMacrosValues dailyMacrosValues) {
                bodyWeight.setText(String.valueOf(dailyMacrosValues.getBodyWeight()));
                intakeSeekBar.setProgress(dailyMacrosValues.getIntakeLevel().floatValue());
                calorieCounter.setText(String.valueOf(dailyMacrosValues.getCalories()) + " kcal");
                carbsSeekBar.setProgress(dailyMacrosValues.getCarbs().floatValue());
                proteinSeekBar.setProgress(dailyMacrosValues.getProtein().floatValue());
                fatSeekBar.setProgress(dailyMacrosValues.getFat().floatValue());

                Log.d(TAG, "DailyMacrosIsUpdated: updating meal plan status");
                MealPlanHelper.MealPlanStatus.DailyValues dailyValues = new MealPlanHelper.MealPlanStatus.DailyValues();

                Double carbsPercent = 0.0, proteinPercent = 0.0, fatPercent = 0.0;

                carbsPercent = dailyMacrosValues.getCarbs() * 0.01;
                proteinPercent = dailyMacrosValues.getProtein() * 0.01;
                fatPercent = dailyMacrosValues.getFat() * 0.01;

                dailyValues.setDailyCalories(dailyMacrosValues.getCalories());
                dailyValues.setDailyCarbs((double)Math.round(dailyMacrosValues.getCalories() * carbsPercent));
                dailyValues.setDailyProtein((double)Math.round(dailyMacrosValues.getCalories() * proteinPercent));
                dailyValues.setDailyFat((double)Math.round(dailyMacrosValues.getCalories() * fatPercent));
                insertToMealPlan(dailyValues);
            }

            @Override
            public void DailyMacrosIsInserted() {

            }

            @Override
            public void DailyMacrosIsUpdated() {

            }

            @Override
            public void DailyMacrosIsDeleted() {

            }
        });
    }

    private void updateFirebase(MealPlanHelper.DailyMacrosValues dailyMacrosValues){
        new FirebaseHelper().updateDailyMacros(new FirebaseHelper.DailyMacrosDataStatus() {
            @Override
            public void DailyMacrosIsLoaded(MealPlanHelper.DailyMacrosValues dailyMacrosValues) {

            }

            @Override
            public void DailyMacrosIsInserted() {

            }

            @Override
            public void DailyMacrosIsUpdated() {
                Log.d(TAG, "DailyMacrosIsUpdated: updating macro values");
            }

            @Override
            public void DailyMacrosIsDeleted() {

            }
        }, dailyMacrosValues);

        donutChart.setCarbs(dailyMacrosValues.getCarbs());
        donutChart.setProtein(dailyMacrosValues.getProtein());
        donutChart.setFat(dailyMacrosValues.getFat());

        new FirebaseHelper().updateDailyDonutChart(new FirebaseHelper.DailyDonutDataStatus() {
            @Override
            public void DailyDonutIsLoaded(MealPlanHelper.DonutChart donutChart) {

            }

            @Override
            public void DailyDonutIsInserted() {

            }

            @Override
            public void DailyDonutIsUpdated(MealPlanHelper.DonutChart donutChart) {
                Log.d(TAG, "DailyDonutIsLoaded: updating donut chart values");
            }

            @Override
            public void DailyDonutIsDeleted() {

            }
        }, donutChart);
    }

    private void insertToMealPlan(MealPlanHelper.MealPlanStatus.DailyValues dailyValues){
        new FirebaseHelper().updateMealPlanStatusFromDailyMacros(new FirebaseHelper.MealPlanStatusDataStatus() {

            @Override
            public void MealPlanStatuslsLoaded(MealPlanHelper.MealPlanStatus.DailyValues dailyValues, MealPlanHelper.MealPlanStatus.ActiveValues activeValues) {

            }

            @Override
            public void MealPlanStatusIsInsertedFromDailyValues() {
                Log.d(TAG, "MealPlanStatusIsInsertedFromDailyValues: inserting to meal plan");
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
        }, dailyValues);
    }
}
