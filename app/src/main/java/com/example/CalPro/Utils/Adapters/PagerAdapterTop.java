package com.example.CalPro.Utils.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.CalPro.Fragments.FragmentAccountSettings.AccoutSettingsFragment;
import com.example.CalPro.Fragments.FragmentLogout.LogoutFragment;
import com.example.CalPro.Fragments.FragmentKitchen.KitchenFragment;
import com.example.CalPro.Fragments.FragmentDashboard.DashboardFragment;
import com.example.CalPro.Fragments.FragmentProgress.ProgressFragment;

import static com.example.CalPro.Utils.CONSTANTS.PAGER_ADAPTER_TOP_NUM_FRAGMENTS;

public class PagerAdapterTop extends SmartFragmentStatePagerAdapter  {

    public PagerAdapterTop(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGER_ADAPTER_TOP_NUM_FRAGMENTS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show LogoutFragment
                return LogoutFragment.newInstance(position + 1);
            case 1: // Fragment # 1 - This will show DashboardFragment
                return DashboardFragment.newInstance(position + 1);
            case 2: // Fragment # 2 - This will show ProgressFragment
                return ProgressFragment.newInstance(position + 1);
            case 3: // Fragment # 3 - This will show KitchenFragment
                return KitchenFragment.newInstance(position + 1);
            case 4: // Fragment # 4 - This will show AccountSettingsFragment
                return AccoutSettingsFragment.newInstance(position + 1);
            default:
                return null;
        }
    }
}
