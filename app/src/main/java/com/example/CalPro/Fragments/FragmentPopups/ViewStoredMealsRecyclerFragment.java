package com.example.CalPro.Fragments.FragmentPopups;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.example.CalPro.Activities.MainActivity;
import com.example.CalPro.R;
import com.example.CalPro.Utils.HelperClasses.FirebaseHelper;
import com.example.CalPro.Utils.HelperClasses.KitchenHelper;
import com.example.CalPro.Utils.Interfaces.FirebaseInterface;
import com.example.CalPro.Utils.Recyclers.KitchenStoredMealsRecycler;
import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static com.example.CalPro.Utils.CONSTANTS.EDIT_STORED_MEALS_FRAGMENT;


public class ViewStoredMealsRecyclerFragment extends Fragment implements FirebaseInterface {
    private static final String TAG = "ViewStoredMealsRecycler";

    private RecyclerView mRecyclerView;
    public List<KitchenHelper.Meal> storedMeals;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_stored_meals_recycler , container, false);
        mRecyclerView = view.findViewById(R.id.viewStoredMealsRecycler);
        storedMeals = new ArrayList<>();
        readFirebase();

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                mRecyclerView.getAdapter().notifyItemChanged(viewHolder.getAdapterPosition());
                ((MainActivity)getActivity()).setActivatedMeal(prepareActivatedMeal(viewHolder.getAdapterPosition()));
                ((MainActivity)getActivity()).toastMessage("Not implemented yet");
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(getContext(), c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white))
                        .addSwipeLeftActionIcon(R.drawable.meals_edit)
                        .addSwipeLeftLabel(getString(R.string.editStoredMealsText))
                        .setSwipeLeftLabelColor(R.color.black)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        return view;
    }


    @Override
    public void readFirebase(){
        final List<KitchenHelper.Meal> mealList = new ArrayList<>();

        new FirebaseHelper().readStoredMeals(new FirebaseHelper.StoredMealsDataStatus() {
            @Override
            public void StoredMealsIsLoaded(List<KitchenHelper.Meal> storedMeals, List<String> keys) {
                new KitchenStoredMealsRecycler().setConfig(mRecyclerView, getContext(), storedMeals, keys);
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
        activatedMeal.setName(name);
        //endregion

        activatedMeal.setMacros(macros);
        return activatedMeal;
    }

    public List<KitchenHelper.Meal> getStoredMeals() {
        return storedMeals;
    }

    public void setStoredMeals(List<KitchenHelper.Meal> storedMeals) {
        this.storedMeals = storedMeals;
    }
}
