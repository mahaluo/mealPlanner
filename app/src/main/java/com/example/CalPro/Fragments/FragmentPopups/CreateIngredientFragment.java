package com.example.CalPro.Fragments.FragmentPopups;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.CalPro.Activities.MainActivity;
import com.example.CalPro.R;
import com.example.CalPro.Utils.HelperClasses.FirebaseHelper;
import com.example.CalPro.Utils.HelperClasses.KitchenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.BigDecimal;
import java.util.List;


public class CreateIngredientFragment extends Fragment {
    private static final String TAG = "CreateIngredientFragment";
    private RadioButton smallGramRatio, bigGramRatio;
    private FloatingActionButton confirmIngredient;
    private EditText carbEditText, proteinEditText, fatEditText, priceEditText, nameEditText;
    private KitchenHelper.Ingredient ingredient;
    private Double calories, carbs, protein, fat, price;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_ingredient, container, false);

        smallGramRatio = view.findViewById(R.id.radioButton10grams);
        bigGramRatio = view.findViewById(R.id.radioButton100grams);
        confirmIngredient = view.findViewById(R.id.storeIngredientButton);
        carbEditText = view.findViewById(R.id.carbsTextInput);
        proteinEditText = view.findViewById(R.id.proteinTextInput);
        fatEditText = view.findViewById(R.id.fatTextInput);
        priceEditText = view.findViewById(R.id.priceTextInput);
        nameEditText = view.findViewById(R.id.ingredientNameTextInput);

        confirmIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (smallGramRatio.isChecked() || bigGramRatio.isChecked()) {
                    if (carbEditText.getText().toString().equals("")) {
                        ((MainActivity)getActivity()).toastMessage("Please give carbs a value");
                    }
                    else if (proteinEditText.getText().toString().equals("")) {
                        ((MainActivity)getActivity()).toastMessage("Please give protein a value");
                    }
                    else if (fatEditText.getText().toString().equals("")) {
                        ((MainActivity)getActivity()).toastMessage("Please give fat a value");
                    }
                    else if (priceEditText.getText().toString().equals("")) {
                        ((MainActivity)getActivity()).toastMessage("Please enter a price");
                    }
                    else if (nameEditText.getText().toString().equals("")) {
                        ((MainActivity)getActivity()).toastMessage("Please enter a name");
                    }
                    else {
                        ingredient = new KitchenHelper.Ingredient();
                        ingredient.setName(nameEditText.getText().toString());
                        setValues();
                        insertFirebase();
                        ((MainActivity)getActivity()).toastMessage(ingredient.getName() + " stored");
                        clearFields();
                    }
                }
                else {
                    ((MainActivity)getActivity()).toastMessage("Please choose ratio first");
                }
            }
        });

        return view;
    }

    private void clearFields() {
        carbEditText.setText("");
        proteinEditText.setText("");
        fatEditText.setText("");
        priceEditText.setText("");
        nameEditText.setText("");
    }

    private void setValues() {

        carbs = Double.parseDouble(carbEditText.getText().toString());
        protein = Double.parseDouble(proteinEditText.getText().toString());
        fat = Double.parseDouble(fatEditText.getText().toString());
        price = Double.parseDouble(priceEditText.getText().toString());
        calories = (carbs * 4) + (protein * 4) + (fat * 9);

        if (bigGramRatio.isChecked()) {
            carbs = (carbs / 10);
            protein = (protein / 10);
            fat = (fat / 10);
            price = (price / 10);
            calories = (calories / 10);
        }

        BigDecimal refinedCarbs = new BigDecimal(String.valueOf(carbs)).setScale(2, BigDecimal.ROUND_HALF_UP);
        ingredient.setCarbs(Double.parseDouble(refinedCarbs.toString()));

        BigDecimal refinedProtein = new BigDecimal(String.valueOf(protein)).setScale(2, BigDecimal.ROUND_HALF_UP);
        ingredient.setProtein(Double.parseDouble(refinedProtein.toString()));

        BigDecimal refinedFat = new BigDecimal(String.valueOf(fat)).setScale(2, BigDecimal.ROUND_HALF_UP);
        ingredient.setFat(Double.parseDouble(refinedFat.toString()));

        BigDecimal refinedPrice = new BigDecimal(String.valueOf(price)).setScale(2, BigDecimal.ROUND_HALF_UP);
        ingredient.setPrice(Double.parseDouble(refinedPrice.toString()));

        BigDecimal refinedCalories = new BigDecimal(String.valueOf(calories)).setScale(2, BigDecimal.ROUND_HALF_UP);
        ingredient.setCalories(Double.parseDouble(refinedCalories.toString()));
    }

    private void insertFirebase() {
        new FirebaseHelper().insertIngredients(new FirebaseHelper.IngredientDataStatus() {
            @Override
            public void IngredientIsLoaded(List<KitchenHelper.Ingredient> ingredients, List<String> keys) {

            }

            @Override
            public void IngredientIsInserted() {
                Log.d(TAG, "IngredientIsInserted: inserted ingredient" + ingredient.getName());
            }

            @Override
            public void IngredientIsUpdated() {

            }

            @Override
            public void IngredientIsDeleted() {

            }
        }, ingredient);
    }
}
