package com.example.CalPro.Fragments.FragmentPopups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.CalPro.Activities.MainActivity;
import com.example.CalPro.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import static com.example.CalPro.Utils.CONSTANTS.ADD_INGREDIENT_TO_MEAL_FRAGMENT;




public class CreateMealFragment extends Fragment {
    private static final String TAG = "CreateMealFragment";
    private EditText mealName;
    private FloatingActionButton confirmMealName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name_new_meal , container, false);
        mealName = view.findViewById(R.id.mealNameEditText);
        confirmMealName = view.findViewById(R.id.createMealButton);

        confirmMealName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mealName.getText().toString().equals("")) {
                    ((MainActivity)getActivity()).setNewMealName(mealName.getText().toString());
                    ((MainActivity)getActivity()).clearFragmentContainer();
                    ((MainActivity)getActivity()).addFragment(ADD_INGREDIENT_TO_MEAL_FRAGMENT);
                }
                else {
                    ((MainActivity)getActivity()).toastMessage("Please enter a name first");
                }
            }
        });



        return view;
    }
}
