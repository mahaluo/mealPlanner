package com.example.CalPro.Fragments.FragmentPopups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.CalPro.R;

public class ErrorFragment extends Fragment {
    private static final String TAG = "ErrorFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getActivity(),TAG,Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_error, container, false);
    }
}
