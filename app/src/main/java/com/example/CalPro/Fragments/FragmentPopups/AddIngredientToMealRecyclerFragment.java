package com.example.CalPro.Fragments.FragmentPopups;

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
import com.example.CalPro.Utils.HelperClasses.KitchenHelper;
import com.example.CalPro.Utils.Interfaces.FirebaseInterface;
import com.example.CalPro.Utils.Recyclers.IngredientRecycler;


import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static com.example.CalPro.Utils.CONSTANTS.INGREDIENT_AMOUNT_FRAGMENT;

public class AddIngredientToMealRecyclerFragment extends Fragment implements FirebaseInterface {
    private static final String TAG = "IngredientAmountFrag";
    private KitchenHelper.Ingredient currentIngredient;
    private TextView mealName;

    private RecyclerView mRecyclerView;

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_ingredient_to_meal , container, false);
        Log.d(TAG, "onCreateView: view created");
        mRecyclerView = view.findViewById(R.id.ingredientRecycler);
        mealName = view.findViewById(R.id.ingredientRecyclerTitleTextView);
        readFirebase();
        mealName.setText(((MainActivity)getActivity()).getNewMealName());

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                ((MainActivity)getActivity()).setCurrentIngredient(prepareCurrentIngredient(viewHolder.getAdapterPosition()));
                addAmountFragment();
                mRecyclerView.getAdapter().notifyItemChanged(viewHolder.getAdapterPosition());
                ((MainActivity)getActivity()).removeFragment(INGREDIENT_AMOUNT_FRAGMENT);
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(getContext(), c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white))
                        .addSwipeLeftActionIcon(R.drawable.ingredients_add)
                        .addSwipeLeftLabel(getString(R.string.addIngredientButton))
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

    private KitchenHelper.Ingredient prepareCurrentIngredient(int position) {
        currentIngredient = new KitchenHelper.Ingredient();

        String name = ((TextView) mRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.ingredientNameTextView)).getText().toString();
        currentIngredient.setName(name);

        String calories = ((TextView) mRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.ingredientCalories)).getText().toString();
        currentIngredient.setCalories(Double.valueOf(calories));

        String carbs = ((TextView) mRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.ingredientCarbs)).getText().toString();
        currentIngredient.setCarbs(Double.valueOf(carbs));

        String protein = ((TextView) mRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.ingredientProtein)).getText().toString();
        currentIngredient.setProtein(Double.valueOf(protein));

        String fat = ((TextView) mRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.ingredientFat)).getText().toString();
        currentIngredient.setFat(Double.valueOf(fat));

        String price = ((TextView) mRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.ingredientPrice)).getText().toString();
        currentIngredient.setPrice(Double.valueOf(price));

        return currentIngredient;
    }

    @Override
    public void readFirebase() {
        new FirebaseHelper().readIngredients(new FirebaseHelper.IngredientDataStatus() {
            @Override
            public void IngredientIsLoaded(List<KitchenHelper.Ingredient> ingredients, List<String> keys) {
                Log.d(TAG, "IngredientIsLoaded: loading ingredients from readFirebase");
                new IngredientRecycler().setConfig(mRecyclerView, getContext(), ingredients, keys);
            }

            @Override
            public void IngredientIsInserted() {

            }

            @Override
            public void IngredientIsUpdated() {

            }

            @Override
            public void IngredientIsDeleted() {

            }
        });

    }

    public void addAmountFragment(){
        ((MainActivity)getActivity()).addFragment(INGREDIENT_AMOUNT_FRAGMENT);
    }
}
