package com.example.CalPro.Utils.Recyclers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.CalPro.Utils.HelperClasses.KitchenHelper;
import com.example.CalPro.R;
import java.util.List;

public class ActiveMealsRecycler {

    private static final String TAG = "ActiveMealsRecycler";
    private Context mContext;
    private ActiveMealsAdapter mActiveMealsAdapter;

    public ActiveMealsRecycler() {
    }

    public void setConfig(RecyclerView recyclerView, Context context, List<KitchenHelper.Meal> activeMealsList, List<String> keys){
        mContext = context;
        mActiveMealsAdapter = new ActiveMealsAdapter(activeMealsList, keys);

        recyclerView.setAdapter(mActiveMealsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
    }

    public class ActiveMealsItemView extends RecyclerView.ViewHolder {

        private TextView activeMealsName;
        private TextView activeMealsPrice;
        private TextView activeMealsCarbs;
        private TextView activeMealsProtein;
        private TextView activeMealsFat;
        private TextView activeMealsCalories;
        private String key;

        public ActiveMealsItemView(ViewGroup parent) {
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
            activeMealsProtein.setText(String.valueOf(meal.getMacros().getPrice()));
            activeMealsFat.setText(String.valueOf(meal.getMacros().getFat()));
            activeMealsCalories.setText(String.valueOf(meal.getMacros().getCalories()));
            this.key = key;
        }
    }

    public class ActiveMealsAdapter extends RecyclerView.Adapter<ActiveMealsItemView>{
        private List<KitchenHelper.Meal> mActiveMealsList;
        private List<String> mKeys;

        public ActiveMealsAdapter(List<KitchenHelper.Meal> mActiveMealsList, List<String> mKeys){
            this.mActiveMealsList = mActiveMealsList;
            this.mKeys = mKeys;
            Log.i("TEST", mActiveMealsList.toString());
        }

        @NonNull
        @Override
        public ActiveMealsItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ActiveMealsItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ActiveMealsItemView holder, int position) {
            holder.bind(mActiveMealsList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mActiveMealsList.size();
        }

    }
}
