package com.example.CalPro.Fragments.FragmentKitchen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.CalPro.Activities.MainActivity;
import com.example.CalPro.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.CalPro.Utils.CONSTANTS.ADD_INGREDIENT_TO_MEAL_FRAGMENT;
import static com.example.CalPro.Utils.CONSTANTS.CREATE_MEAL_FRAGMENT;
import static com.example.CalPro.Utils.CONSTANTS.NEW_INGREDIENT_FRAGMENT;
import static com.example.CalPro.Utils.CONSTANTS.NEW_MEAL_FRAGMENT;
import static com.example.CalPro.Utils.CONSTANTS.STORED_INGREDIENTS_FRAGMENT;
import static com.example.CalPro.Utils.CONSTANTS.STORED_MEALS_FRAGMENT;

public class KitchenFragment extends Fragment {

    private int page;
    private FloatingActionButton newMealButton, newIngredientButton, storedMealsButton, storedIngredientsButton;

    // newInstance constructor for creating fragment with arguments
    public static KitchenFragment newInstance(int page) {
        KitchenFragment fragmentFirst = new KitchenFragment();
        Bundle args = new Bundle();
        args.putInt("4", page);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("4", 0);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kitchen, container, false);
        TextView tvLabel = view.findViewById(R.id.kitchenTitleTextView);
        tvLabel.setText(R.string.kitchenTitle);

        newMealButton = view.findViewById(R.id.newMealButton);
        newIngredientButton = view.findViewById(R.id.newIngredientButton);
        storedMealsButton = view.findViewById(R.id.storedMealsButton);
        storedIngredientsButton = view.findViewById(R.id.storedIngredientsButton);

        newMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).clearFragmentContainer();
                ((MainActivity)getActivity()).addFragment(CREATE_MEAL_FRAGMENT);
            }
        });

        newIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).clearFragmentContainer();
                ((MainActivity)getActivity()).addFragment(NEW_INGREDIENT_FRAGMENT);
            }
        });

        storedMealsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).clearFragmentContainer();
                ((MainActivity)getActivity()).addFragment(STORED_MEALS_FRAGMENT);
            }
        });

        storedIngredientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).clearFragmentContainer();
                ((MainActivity)getActivity()).addFragment(STORED_INGREDIENTS_FRAGMENT);
            }
        });

        return view;
    }

}
