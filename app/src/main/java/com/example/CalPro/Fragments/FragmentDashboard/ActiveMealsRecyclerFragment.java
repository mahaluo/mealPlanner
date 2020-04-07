package com.example.CalPro.Fragments.FragmentDashboard;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.example.CalPro.Activities.MainActivity;
import com.example.CalPro.R;
import com.example.CalPro.Utils.HelperClasses.FirebaseHelper;
import com.example.CalPro.Utils.Interfaces.FirebaseInterface;
import com.example.CalPro.Utils.HelperClasses.MealPlanHelper;
import com.example.CalPro.Utils.Recyclers.ActiveMealsRecycler;
import com.example.CalPro.Utils.HelperClasses.KitchenHelper;
import java.util.List;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class ActiveMealsRecyclerFragment extends Fragment implements FirebaseInterface {
    private static final String TAG = "ActiveMealsRecyclerFrag";
    private int page;

    private RecyclerView mRecyclerView;
    public MealPlanHelper.MealPlanStatus.ActiveValues activeValues = new MealPlanHelper.MealPlanStatus.ActiveValues();
    // newInstance constructor for creating fragment with arguments
    public static ActiveMealsRecyclerFragment newInstance(int page) {
        ActiveMealsRecyclerFragment fragmentFirst = new ActiveMealsRecyclerFragment();
        Bundle args = new Bundle();
        args.putInt("3", page);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: active meals recycler fragment created");
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("3", 0);
        //readFirebase();
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_active_meals_recycler, container, false);
        mRecyclerView = view.findViewById(R.id.activeMealsRecycler);

        readFirebase();

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                
                switch(direction) {
                    case ItemTouchHelper.LEFT:
                        deleteFirebase(viewHolder.getAdapterPosition());
                        ((MainActivity)getActivity()).toastMessage("Meal removed!");
                        Log.d(TAG, "onSwiped: removed meal from active list");

                        break;
                    default:
                        break;
                }

                mRecyclerView.getAdapter().notifyItemChanged(viewHolder.getAdapterPosition());
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(getContext(), c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white))
                        .addSwipeLeftActionIcon(R.drawable.meals_remove)
                        .addSwipeLeftLabel(getString(R.string.activeMealsDecorationLabelRemove))
                        .setSwipeLeftLabelColor(R.color.black)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        return view;
    }

    private void insertToMealPlan(MealPlanHelper.MealPlanStatus.ActiveValues activeValues) {
        new FirebaseHelper().updateMealPlanStatusFromActiveMeals(new FirebaseHelper.MealPlanStatusDataStatus() {

            @Override
            public void MealPlanStatuslsLoaded(MealPlanHelper.MealPlanStatus.DailyValues dailyValues, MealPlanHelper.MealPlanStatus.ActiveValues activeValues) {

            }

            @Override
            public void MealPlanStatusIsInsertedFromDailyValues() {

            }

            @Override

            public void MealPlanStatusIsInsertedFromActiveValues() {
                Log.d(TAG, "MealPlanStatusIsInsertedFromActiveValues: inserting to meal plan");
            }

            @Override
            public void MealPlanStatusIsUpdated() {

            }

            @Override
            public void MealPlanStatusIsDeleted() {

            }
        }, activeValues);
    }

    private void deleteFirebase(int position) {
        String mealName = ((TextView) mRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.activeMealsNameTextView)).getText().toString();
        new FirebaseHelper().deleteActiveMeals(new FirebaseHelper.ActiveMealsDataStatus() {

            @Override
            public void ActiveMealsIsLoaded(List<KitchenHelper.Meal> activeMeals, List<String> keys) {

            }

            @Override
            public void ActiveMealsIsInserted() {

            }

            @Override
            public void ActiveMealsIsUpdated() {

            }

            @Override
            public void ActiveMealsIsDeleted() {

            }
        }, mealName);
    }

    @Override
    public void readFirebase(){
        new FirebaseHelper().readActiveMeals(new FirebaseHelper.ActiveMealsDataStatus() {
            @Override
            public void ActiveMealsIsLoaded(List<KitchenHelper.Meal> activeMeals, List<String> keys) {
                Log.d(TAG, "ActiveMealsIsLoaded: loading active meals from readFirebase");
                ((MainActivity)getActivity()).setActiveMealsValues(activeMeals);
                new ActiveMealsRecycler().setConfig(mRecyclerView, getContext(), activeMeals, keys);

                activeValues.setActiveCalories(0.0);
                activeValues.setActiveCarbs(0.0);
                activeValues.setActiveProtein(0.0);
                activeValues.setActiveFat(0.0);
                activeValues.setCost(0.0);

                Double calories = 0.0, carbs = 0.0, protein = 0.0, fat = 0.0, cost = 0.0;

                for (int i = 0; i < activeMeals.size(); i++){
                    calories = calories + activeMeals.get(i).getMacros().getCalories();
                    carbs = carbs + activeMeals.get(i).getMacros().getCarbs();
                    protein = protein + activeMeals.get(i).getMacros().getProtein();
                    fat = fat + activeMeals.get(i).getMacros().getFat();
                    cost = cost + activeMeals.get(i).getMacros().getPrice();
                }

                activeValues.setActiveCalories(calories);
                activeValues.setActiveCarbs(carbs);
                activeValues.setActiveProtein(protein);
                activeValues.setActiveFat(fat);
                activeValues.setCost(cost);

                insertToMealPlan(activeValues);
            }

            @Override
            public void ActiveMealsIsInserted() {

            }

            @Override
            public void ActiveMealsIsUpdated() {

            }

            @Override
            public void ActiveMealsIsDeleted() {

            }
        });
    }


}
