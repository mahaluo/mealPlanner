package com.example.CalPro.Utils.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.CalPro.Fragments.FragmentProgress.EditChartFragment;
import com.example.CalPro.Fragments.FragmentProgress.NewChartFragment;
import com.example.CalPro.Fragments.FragmentProgress.NewProgressEntryFragment;
import static com.example.CalPro.Utils.CONSTANTS.PAGER_ADAPTER_BOTTOM_PROGRESS_NUM_FRAGMENTS;

public class PagerAdapterBottomProgress extends SmartFragmentStatePagerAdapter {

    public PagerAdapterBottomProgress(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show date pick new entry fragment
                return NewProgressEntryFragment.newInstance(position + 1);
            case 1: // Fragment # 1 - This will show edit entry fragment
                return EditChartFragment.newInstance(position + 1);
            case 2: // Fragment # 2 - This will show save chart and start new chart fragment
                return NewChartFragment.newInstance(position + 1);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGER_ADAPTER_BOTTOM_PROGRESS_NUM_FRAGMENTS;
    }
}
