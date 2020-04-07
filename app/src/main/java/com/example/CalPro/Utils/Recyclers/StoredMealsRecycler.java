package com.example.CalPro.Utils.Recyclers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CalPro.Activities.MainActivity;
import com.example.CalPro.R;
import com.example.CalPro.Utils.HelperClasses.KitchenHelper;
import java.util.List;

public class StoredMealsRecycler {

    private static final String TAG = "StoredMealsRecycler";
    private Context mContext;
    private StoredMealsAdapter mStoredMealsAdapter;

    public StoredMealsRecycler() {
    }

    public void setConfig(RecyclerView recyclerView, Context context, List<KitchenHelper.Meal> storedMealList, List<String> keys){
        mContext = context;
        mStoredMealsAdapter = new StoredMealsAdapter(storedMealList, keys);

        recyclerView.setAdapter(mStoredMealsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
    }

    public class StoredMealsItemView extends RecyclerView.ViewHolder  {

        private TextView activeMealsName;
        private TextView activeMealsPrice;
        private TextView activeMealsCarbs;
        private TextView activeMealsProtein;
        private TextView activeMealsFat;
        private TextView activeMealsCalories;
        private String key;

        public StoredMealsItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.recycler_active_meal_view, parent, false));

            activeMealsName = itemView.findViewById(R.id.activeMealsNameTextView);
            activeMealsPrice = itemView.findViewById(R.id.activeMealsPrice);
            activeMealsCarbs = itemView.findViewById(R.id.activeMealsCarbs);
            activeMealsProtein = itemView.findViewById(R.id.activeMealsProtein);
            activeMealsFat = itemView.findViewById(R.id.activeMealsFat);
            activeMealsCalories = itemView.findViewById(R.id.activeMealsCalories);
        }

        public void bind(KitchenHelper.Meal meal, String key){
            activeMealsName.setText(key);
            activeMealsPrice.setText(String.valueOf(meal.getMacros().getPrice()));
            activeMealsCarbs.setText(String.valueOf(meal.getMacros().getCarbs()));
            activeMealsProtein.setText(String.valueOf(meal.getMacros().getProtein()));
            activeMealsFat.setText(String.valueOf(meal.getMacros().getFat()));
            activeMealsCalories.setText(String.valueOf(meal.getMacros().getCalories()));
            this.key = key;
        }
    }

    public class StoredMealsAdapter extends RecyclerView.Adapter<StoredMealsItemView>{
        private List<KitchenHelper.Meal> mStoredMealList;
        private List<String> mKeys;

        public StoredMealsAdapter(List<KitchenHelper.Meal> mStoredMealList, List<String> mKeys){
            this.mStoredMealList = mStoredMealList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public StoredMealsItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new StoredMealsItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull StoredMealsItemView holder, int position) {
            holder.bind(mStoredMealList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mStoredMealList.size();
        }
    }
}
