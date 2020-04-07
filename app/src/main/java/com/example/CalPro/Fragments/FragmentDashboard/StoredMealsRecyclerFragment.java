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
import com.example.CalPro.Utils.HelperClasses.KitchenHelper;
import com.example.CalPro.Utils.Recyclers.StoredMealsRecycler;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class StoredMealsRecyclerFragment extends Fragment implements FirebaseInterface {
    private static final String TAG = "StoredMealsRecyclerFrag";
    private int page;

    private RecyclerView mRecyclerView;
    private List<KitchenHelper.Meal> storedMeals;
    private List<KitchenHelper.Meal> activeMeals;

    // newInstance constructor for creating fragment with arguments
    public static StoredMealsRecyclerFragment newInstance(int page) {
        StoredMealsRecyclerFragment fragmentFirst = new StoredMealsRecyclerFragment();
        Bundle args = new Bundle();
        args.putInt("4", page);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: saved meals recycler created");
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("4", 0);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stored_meals_recycler, container, false);
        mRecyclerView = view.findViewById(R.id.storedMealsRecycler);
        storedMeals = new ArrayList<>();
        activeMeals = ((MainActivity)getActivity()).getActiveMeals();
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
                        insertToActiveMeals(prepareActivatedMeal(viewHolder.getAdapterPosition()));
                        ((MainActivity)getActivity()).toastMessage("Meal added!");
                        Log.d(TAG, "onSwiped: added meal to active list");

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
                        .addSwipeLeftActionIcon(R.drawable.meals_add)
                        .addSwipeLeftLabel(getString(R.string.storedMealsDecorationLabelActivate))
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

    private KitchenHelper.Meal prepareActivatedMeal(int position) {

        KitchenHelper.Meal activatedMeal = new KitchenHelper.Meal();
        KitchenHelper.Macros macros = new KitchenHelper.Macros();

        //region setvalues
        String calories = ((TextView) mRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.activeMealsCalories)).getText().toString();
        macros.setCalories(Double.valueOf(calories));

        String carbs = ((TextView) mRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.activeMealsCarbs)).getText().toString();
        macros.setCarbs(Double.valueOf(carbs));

        String protein = ((TextView) mRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.activeMealsProtein)).getText().toString();
        macros.setProtein(Double.valueOf(protein));

        String fat = ((TextView) mRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.activeMealsFat)).getText().toString();
        macros.setFat(Double.valueOf(fat));

        String price = ((TextView) mRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.activeMealsPrice)).getText().toString();
        macros.setPrice(Double.valueOf(price));

        String name = ((TextView) mRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.activeMealsNameTextView)).getText().toString();
        //endregion
        boolean mealFound = false;
        for (int i = 0; i < activeMeals.size(); i++) {
            if (activeMeals.get(i).getName().equals(name)) {
                mealFound = true;
            }
        }
        if (!mealFound) {
            activatedMeal.setName(name);
        } else {
            activatedMeal.setName(name + activeMeals.size() + 1);
        }
        activatedMeal.setMacros(macros);
        return activatedMeal;
    }

    private void insertToActiveMeals(final KitchenHelper.Meal activatedMeal){
        new FirebaseHelper().insertActiveMeals(new FirebaseHelper.ActiveMealsDataStatus() {
            @Override
            public void ActiveMealsIsLoaded(List<KitchenHelper.Meal> activeMeals, List<String> keys) {

            }

            @Override
            public void ActiveMealsIsInserted() {
                Log.d(TAG, "StoredMealsIsInserted: activating meal " + activatedMeal.getName());
            }

            @Override
            public void ActiveMealsIsUpdated() {

            }

            @Override
            public void ActiveMealsIsDeleted() {

            }
        }, activatedMeal);
    }

    @Override
    public void readFirebase(){
        final List<KitchenHelper.Meal> mealList = new ArrayList<>();

        new FirebaseHelper().readStoredMeals(new FirebaseHelper.StoredMealsDataStatus() {
            @Override
            public void StoredMealsIsLoaded(List<KitchenHelper.Meal> storedMeals, List<String> keys) {
                new StoredMealsRecycler().setConfig(mRecyclerView, getContext(), storedMeals, keys);
                mealList.addAll(storedMeals);
            }

            @Override
            public void StoredMealsIsInserted() {

            }

            @Override
            public void StoredMealsIsUpdated() {

            }

            @Override
            public void StoredMealsIsDeleted() {

            }
        });

        setStoredMeals(mealList);
    }

    public List<KitchenHelper.Meal> getStoredMeals() {
        return storedMeals;
    }

    public void setStoredMeals(List<KitchenHelper.Meal> storedMeals) {
        this.storedMeals = storedMeals;
    }
}
