package com.example.CalPro.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.example.CalPro.Fragments.FragmentPopups.AddIngredientToMealRecyclerFragment;
import com.example.CalPro.Fragments.FragmentPopups.CreateIngredientFragment;
import com.example.CalPro.Fragments.FragmentPopups.CreateMealFragment;
import com.example.CalPro.Fragments.FragmentPopups.IngredientAmountFragment;
import com.example.CalPro.Fragments.FragmentPopups.EditStoredMealFragment;
import com.example.CalPro.Fragments.FragmentPopups.ErrorFragment;
import com.example.CalPro.Fragments.FragmentPopups.ViewIngredientRecyclerFragment;
import com.example.CalPro.Fragments.FragmentPopups.NewWeightEntryFragment;
import com.example.CalPro.Fragments.FragmentPopups.ViewStoredMealsRecyclerFragment;
import com.example.CalPro.Fragments.FragmentProgress.NewProgressEntryFragment;
import com.example.CalPro.Utils.Adapters.PagerAdapterBottomDashboard;
import com.example.CalPro.Utils.Adapters.PagerAdapterBottomProgress;
import com.example.CalPro.Utils.Adapters.PagerAdapterTop;
import com.example.CalPro.Utils.Adapters.SmartFragmentStatePagerAdapter;
import com.example.CalPro.Utils.HelperClasses.KitchenHelper;
import com.example.CalPro.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;
import java.util.List;
import static com.example.CalPro.Utils.CONSTANTS.ADD_INGREDIENT_TO_MEAL_FRAGMENT;
import static com.example.CalPro.Utils.CONSTANTS.CREATE_MEAL_FRAGMENT;
import static com.example.CalPro.Utils.CONSTANTS.EDIT_STORED_MEALS_FRAGMENT;
import static com.example.CalPro.Utils.CONSTANTS.ERROR_FRAGMENT;
import static com.example.CalPro.Utils.CONSTANTS.INGREDIENT_AMOUNT_FRAGMENT;
import static com.example.CalPro.Utils.CONSTANTS.NEW_INGREDIENT_FRAGMENT;
import static com.example.CalPro.Utils.CONSTANTS.NEW_MEAL_FRAGMENT;
import static com.example.CalPro.Utils.CONSTANTS.NEW_WEIGHT_ENTRY;
import static com.example.CalPro.Utils.CONSTANTS.STORED_INGREDIENTS_FRAGMENT;
import static com.example.CalPro.Utils.CONSTANTS.STORED_MEALS_FRAGMENT;

public class MainActivity extends AppCompatActivity {

    //region
    private static final String TAG = "MainActivity";
    private SmartFragmentStatePagerAdapter adapterViewPagerTop, adapterViewPagerBottomDashboard, adapterViewPagerBottomProgress;
    private TabLayout tabLayout;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<KitchenHelper.Meal> activeMeals = new ArrayList<>();
    public KitchenHelper.Meal activatedMeal;
    private Double weightEntry;
    private View fragmentView;
    private String newMealName;
    private String newIngredientName;
    private KitchenHelper.Ingredient currentIngredient;
    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: created main activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager vpPagerTop = findViewById(R.id.vpPagerTop);
        final ViewPager vpPagerBottomDashboard = findViewById(R.id.vpPagerBottomDashboard);
        final ViewPager vpPagerBottomProgress = findViewById(R.id.vpPagerBottomProgress);

        adapterViewPagerTop = new PagerAdapterTop(getSupportFragmentManager());
        adapterViewPagerBottomDashboard = new PagerAdapterBottomDashboard(getSupportFragmentManager());
        adapterViewPagerBottomProgress = new PagerAdapterBottomProgress((getSupportFragmentManager()));

        fragmentManager = getSupportFragmentManager();
        vpPagerTop.setAdapter(adapterViewPagerTop);

        //region pageTransformers
        vpPagerTop.setOffscreenPageLimit(5);
        vpPagerTop.setPageTransformer(true, new CubeOutTransformer());

        vpPagerBottomDashboard.setOffscreenPageLimit(4);
        vpPagerBottomDashboard.setPageTransformer(true, new CubeOutTransformer());

        vpPagerBottomProgress.setOffscreenPageLimit(3);
        vpPagerBottomProgress.setPageTransformer(true, new CubeOutTransformer());
        //endregion

        //region onPageListeners
        vpPagerTop.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {

                clearFragmentContainer();
                vpPagerBottomDashboard.setAdapter(null);
                vpPagerBottomProgress.setAdapter(null);
                clearFragmentContainer();
                switch(position){

                    case 0:
                        //sign out fragment
                        break;
                    case 1:
                        //dashboard fragment
                        vpPagerBottomDashboard.setAdapter(adapterViewPagerBottomDashboard);
                        break;
                    case 2:
                        //progress fragment
                        vpPagerBottomProgress.setAdapter(adapterViewPagerBottomProgress);
                        break;
                    case 3:
                        //kitchen fragment

                        break;
                    case 4:
                        //account settings fragment
                        break;
                    default:
                        break;
                }

            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here

            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
                switch(state) {
                    case 0:

                        break;

                    case 1:


                        break;

                    case 2:

                        break;
                }

            }
        });

        vpPagerBottomDashboard.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                switch(position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        vpPagerBottomProgress.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //endregion

        tabLayout = findViewById(R.id.tabs);
        setupTabLayout(vpPagerTop);
        tabLayout.getTabAt(1).select();
        vpPagerBottomDashboard.setCurrentItem(0);
    }

    public void setupTabLayout(ViewPager vpPager) {
        tabLayout.setupWithViewPager(vpPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_logout);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_dashboard);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_progress);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_kitchen);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_account_settings);
    }

    //region fragment functions
    public void addFragment(String fragmentCode){
        Log.d(TAG, "addFragment: adding fragment " + fragmentCode);
        Fragment fragment;

        switch(fragmentCode){
            case ERROR_FRAGMENT: fragment = new ErrorFragment();
                break;
            case NEW_WEIGHT_ENTRY: fragment = new NewWeightEntryFragment();
                break;
            case NEW_MEAL_FRAGMENT: fragment = new IngredientAmountFragment();
                break;
            case NEW_INGREDIENT_FRAGMENT: fragment = new CreateIngredientFragment();
                break;
            case STORED_MEALS_FRAGMENT: fragment = new ViewStoredMealsRecyclerFragment();
                break;
            case STORED_INGREDIENTS_FRAGMENT: fragment = new ViewIngredientRecyclerFragment();
                break;
            case EDIT_STORED_MEALS_FRAGMENT: fragment = new EditStoredMealFragment();
                break;
            case ADD_INGREDIENT_TO_MEAL_FRAGMENT: fragment = new AddIngredientToMealRecyclerFragment();
                break;
            case INGREDIENT_AMOUNT_FRAGMENT: fragment = new IngredientAmountFragment();
                break;
            case CREATE_MEAL_FRAGMENT: fragment = new CreateMealFragment();
                break;
            default: fragment = new ErrorFragment();
                break;
        }

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.FragmentContainer, fragment, fragmentCode);
        fragmentTransaction.addToBackStack(fragmentCode);
        fragmentList.add(fragment);
        fragmentTransaction.commit();
    }
    public void removeFragment(String fragmentCode) {
        Log.d(TAG, "removeFragment: removing fragment " + fragmentCode);

        Fragment fragment = fragmentManager.findFragmentByTag(fragmentCode);
        if (fragment != null) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }

    }
    public void clearFragmentContainer(){
        Log.d(TAG, "removeFragmentOnPageScrolled: Clearing fragment container of fragments");
        for (int i = 0; i < fragmentList.size(); i++) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragmentList.get(i));
            fragmentTransaction.commit();
        }
    }
    private void replaceFragment(){
        Log.d(TAG, "replaceFragment: Replacing fragment");

    }
    public void countFragment(TextView fragmentCount){
        fragmentCount.setText("Fragment count in back stack:"+fragmentManager.getBackStackEntryCount());
    }
    public void removeErrorFragment(View view){
        clearFragmentContainer();
    }
    //endregion

    public void message(View view) {
        toastMessage("toot");
    }

    public void setActiveMealsValues(List<KitchenHelper.Meal> activeMeals) {
        this.activeMeals.clear();
        this.activeMeals.addAll(activeMeals);
    }

    public List<KitchenHelper.Meal> getActiveMeals(){
        return activeMeals;
    }

    public void logout(View view){
        Log.d(TAG, "logout: logging out user");;
        FirebaseAuth.getInstance().signOut();
        Intent logout = new Intent(this, LoginActivity.class);
        startActivity(logout);
        toastMessage("Logged out!");
    }

    public void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void setView(View view) {
        fragmentView = view;
    }

    public View getFragmentView(){
        return fragmentView;
    }

    public Double getWeightEntry() {
        return weightEntry;
    }

    public void setWeightEntry(Double weightEntry) {
        this.weightEntry = weightEntry;

        NewProgressEntryFragment fragment = (NewProgressEntryFragment) adapterViewPagerBottomProgress.getRegisteredFragment(0);
        fragment.setWeightEntry(weightEntry);
        fragment.insertFirebase();
        toastMessage("New weight entry added");

        Log.d(TAG, "setWeightEntry: removing fragment");
        removeFragment(NEW_WEIGHT_ENTRY);
    }

    public void removeWeightEntryFragment(View view) {

        removeFragment(NEW_WEIGHT_ENTRY);
    }

    public void setActivatedMeal(KitchenHelper.Meal prepareActivatedMeal) {
        activatedMeal = new KitchenHelper.Meal();
        activatedMeal = prepareActivatedMeal;
    }

    public KitchenHelper.Meal getActivatedMeal() {
        return activatedMeal;
    }

    public void setNewMealName(String mealName){
        this.newMealName = mealName;
    }

    public String getNewMealName(){
        return this.newMealName;
    }

    public void setCurrentIngredient(KitchenHelper.Ingredient currentIngredient){
        this.currentIngredient = new KitchenHelper.Ingredient();
        this.currentIngredient = currentIngredient;
    }

    public KitchenHelper.Ingredient getCurrentIngredient() {
        return this.currentIngredient;
    }

    public void setNewIngredientName(String ingredientName) {
        this.newIngredientName = ingredientName;
    }

    public String getNewIngredientName(){
        return this.newIngredientName;
    }
}
