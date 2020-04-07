package com.example.CalPro.Fragments.FragmentPopups;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.CalPro.Activities.MainActivity;
import com.example.CalPro.R;
import com.example.CalPro.Utils.HelperClasses.KitchenHelper;
import com.example.CalPro.Utils.Recyclers.EditMealIngredientsRecycler;

import java.util.ArrayList;
import java.util.List;


public class EditStoredMealFragment extends Fragment {
    private static final String TAG = "EditStoredMealFragment";
    private KitchenHelper.Meal activatedMeal;
    private List<KitchenHelper.Ingredient> ingredientList;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_meal , container, false);
        Log.d(TAG, "onCreateView: view created");
        activatedMeal = new KitchenHelper.Meal();
        activatedMeal = ((MainActivity)getActivity()).getActivatedMeal();
        setIngredientList(activatedMeal);
        mRecyclerView = view.findViewById(R.id.editMealRecyclerView);
       // new EditMealIngredientsRecycler().setConfig(mRecyclerView, getContext(), ingredients, keys);


        return view;
    }

    private void setIngredientList(KitchenHelper.Meal activatedMeal) {
        ingredientList = new ArrayList<>();
        //ingredientList.add(activatedMeal.getCalories());


    }
}
