package com.example.CalPro.Fragments.FragmentLogout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.CalPro.R;

public class LogoutFragment extends Fragment {

    private int page;

    // newInstance constructor for creating fragment with arguments
    public static LogoutFragment newInstance(int page) {
        LogoutFragment fragmentFirst = new LogoutFragment();
        Bundle args = new Bundle();
        args.putInt("1", page);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("1", 0);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logout, container, false);
        TextView tvLabel = view.findViewById(R.id.logoutTitleTextView);
        tvLabel.setText(R.string.logoutTitle);
        return view;
    }

}
