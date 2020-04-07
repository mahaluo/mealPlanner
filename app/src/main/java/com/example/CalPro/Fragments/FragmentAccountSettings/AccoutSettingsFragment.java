package com.example.CalPro.Fragments.FragmentAccountSettings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.CalPro.R;

public class AccoutSettingsFragment extends Fragment {
    private static final String TAG = "AccoutSettingsFragment";
    private int page;


    // newInstance constructor for creating fragment with arguments
    public static AccoutSettingsFragment newInstance(int page) {
        AccoutSettingsFragment fragmentFirst = new AccoutSettingsFragment();
        Bundle args = new Bundle();
        args.putInt("5", page);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("5", 0);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_settings, container, false);
        TextView tvLabel = view.findViewById(R.id.accountSettingsTitleTextView);
        tvLabel.setText(R.string.accountSettingsTitle);
        return view;
    }
}
