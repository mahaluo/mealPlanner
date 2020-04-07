package com.example.CalPro.Fragments.FragmentPopups;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.CalPro.Activities.MainActivity;
import com.example.CalPro.R;
import com.example.CalPro.Utils.HelperClasses.FirebaseHelper;
import com.github.mikephil.charting.data.Entry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class NewWeightEntryFragment extends Fragment {

    private static final String TAG = "NewWeightEntryFragment";
    private EditText weightEntry;
    private FloatingActionButton confirmEntry;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: new weight entry fragment created");
        return inflater.inflate(R.layout.fragment_new_weight_entry, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        confirmEntry = view.findViewById(R.id.confirmWeightEntryButton);
        weightEntry = view.findViewById(R.id.newWeightEditText);

        confirmEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!weightEntry.getText().toString().equals("")) {

                    ((MainActivity)getActivity()).setWeightEntry(Double.parseDouble(weightEntry.getText().toString()));
                }
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }
}
