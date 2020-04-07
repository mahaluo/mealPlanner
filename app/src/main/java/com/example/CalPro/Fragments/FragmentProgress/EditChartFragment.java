package com.example.CalPro.Fragments.FragmentProgress;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.CalPro.R;
import com.example.CalPro.Utils.Interfaces.FirebaseInterface;

public class EditChartFragment extends Fragment implements FirebaseInterface {

    private static final String TAG = "EditChartFragment";
    private int page;

    public static Fragment newInstance(int page) {
        EditChartFragment fragmentFirst = new EditChartFragment();
        Bundle args = new Bundle();
        args.putInt("2", page);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: edit chart fragment created");
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("2", 0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_chart, container, false);
        readFirebase();
        return view;
    }

    @Override
    public void readFirebase() {

    }
}
