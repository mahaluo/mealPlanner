package com.example.CalPro.Fragments.FragmentPopups;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CalPro.Activities.MainActivity;
import com.example.CalPro.R;
import com.example.CalPro.Utils.HelperClasses.FirebaseHelper;
import com.example.CalPro.Utils.Interfaces.FirebaseInterface;
import com.example.CalPro.Utils.Recyclers.IngredientRecycler;
import com.example.CalPro.Utils.HelperClasses.KitchenHelper;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ViewIngredientRecyclerFragment extends Fragment implements FirebaseInterface {
    private static final String TAG = "ViewIngredientRecyclerF";

    private RecyclerView mRecyclerView;

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ingredient_recycler , container, false);
        Log.d(TAG, "onCreateView: view created");
        mRecyclerView = view.findViewById(R.id.ingredientRecycler);

        readFirebase();

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                mRecyclerView.getAdapter().notifyItemChanged(viewHolder.getAdapterPosition());

                ((MainActivity)getActivity()).toastMessage("Not implemented yet");
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(getContext(), c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white))
                        .addSwipeLeftActionIcon(R.drawable.ingredients_edit)
                        .addSwipeLeftLabel(getString(R.string.editIngredientTextView))
                        .setSwipeLeftLabelColor(R.color.black)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        return view;
    }

    @Override
    public void readFirebase() {
        new FirebaseHelper().readIngredients(new FirebaseHelper.IngredientDataStatus() {
            @Override
            public void IngredientIsLoaded(List<KitchenHelper.Ingredient> ingredients, List<String> keys) {
                Log.d(TAG, "IngredientIsLoaded: loading ingredients from readFirebase");
                new IngredientRecycler().setConfig(mRecyclerView, getContext(), ingredients, keys);
            }

            @Override
            public void IngredientIsInserted() {

            }

            @Override
            public void IngredientIsUpdated() {

            }

            @Override
            public void IngredientIsDeleted() {

            }
        });
    }
}
