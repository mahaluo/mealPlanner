package com.example.CalPro.Utils.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.CalPro.Fragments.FragmentDashboard.ChangeMacrosFragment;
import com.example.CalPro.Fragments.FragmentDashboard.MealPlanStatusFragment;
import com.example.CalPro.Fragments.FragmentDashboard.ActiveMealsRecyclerFragment;
import com.example.CalPro.Fragments.FragmentDashboard.StoredMealsRecyclerFragment;

import static com.example.CalPro.Utils.CONSTANTS.PAGER_ADAPTER_BOTTOM_DASHBOARD_NUM_FRAGMENTS;

public class PagerAdapterBottomDashboard extends SmartFragmentStatePagerAdapter  {

    public PagerAdapterBottomDashboard(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGER_ADAPTER_BOTTOM_DASHBOARD_NUM_FRAGMENTS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show MealPlanStatusFragment
                return MealPlanStatusFragment.newInstance(position + 1);
            case 1: // Fragment # 1 - This will show ChangeMacrosFragment
                return ChangeMacrosFragment.newInstance(position + 1);
            case 2: // Fragment # 2 - This will show ActiveMealsFragment
                return ActiveMealsRecyclerFragment.newInstance(position + 1);
            case 3: // Fragment # 3 - This will show StoredMealsFragment
                return StoredMealsRecyclerFragment.newInstance(position + 1);
            default:
                return null;
        }
    }
}
