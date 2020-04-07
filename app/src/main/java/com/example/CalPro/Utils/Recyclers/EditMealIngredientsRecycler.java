package com.example.CalPro.Utils.Recyclers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CalPro.R;
import com.example.CalPro.Utils.HelperClasses.KitchenHelper;

import java.util.List;

public class EditMealIngredientsRecycler {

    private Context mContext;
    private MealIngredientAdapter mMealIngredientsAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<KitchenHelper.Ingredient> ingredientList, List<String> keys){
        mContext = context;
        mMealIngredientsAdapter = new MealIngredientAdapter(ingredientList, keys);
        recyclerView.setAdapter(mMealIngredientsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
    }

    class MealIngredientItemView extends RecyclerView.ViewHolder {

        private TextView ingredientName;
        private TextView ingredientPrice;
        private TextView ingredientCarbs;
        private TextView ingredientProtein;
        private TextView ingredientFat;
        private TextView ingredientCalories;
        private String key;

        public MealIngredientItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.recycler_ingredient_view, parent, false));
            ingredientName = itemView.findViewById(R.id.ingredientNameTextView);
            ingredientPrice = itemView.findViewById(R.id.ingredientPrice);
            ingredientCarbs = itemView.findViewById(R.id.ingredientCarbs);
            ingredientProtein = itemView.findViewById(R.id.ingredientProtein);
            ingredientFat = itemView.findViewById(R.id.ingredientFat);
            ingredientCalories = itemView.findViewById(R.id.ingredientCalories);
        }

        public void bind(KitchenHelper.Ingredient ingredient, String key){
            ingredientName.setText(key);
            ingredientPrice.setText(String.valueOf(ingredient.getPrice()));
            ingredientCarbs.setText(String.valueOf(ingredient.getCarbs()));
            ingredientProtein.setText(String.valueOf(ingredient.getProtein()));
            ingredientFat.setText(String.valueOf(ingredient.getFat()));
            ingredientCalories.setText(String.valueOf(ingredient.getCalories()));
            this.key = key;
        }
    }

    class MealIngredientAdapter extends RecyclerView.Adapter<MealIngredientItemView>{
        private List<KitchenHelper.Ingredient> mIngredientList;
        private List<String> mKeys;

        public MealIngredientAdapter(List<KitchenHelper.Ingredient> mIngredientList, List<String> mKeys) {
            this.mIngredientList = mIngredientList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public MealIngredientItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MealIngredientItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MealIngredientItemView holder, int position) {
            holder.bind(mIngredientList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mIngredientList.size();
        }
    }
}
