package com.example.CalPro.Fragments.FragmentDashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.CalPro.R;
import com.example.CalPro.Utils.HelperClasses.FirebaseHelper;
import com.example.CalPro.Utils.Interfaces.FirebaseInterface;
import com.example.CalPro.Utils.HelperClasses.MealPlanHelper;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.example.CalPro.Utils.CONSTANTS.EMPTY_DONUT;


public class DashboardFragment extends Fragment implements FirebaseInterface {
    private static final String TAG = "DashboardFragment";
    private int page;
    public PieChart donut;
    private List<PieEntry> macronutrients = new ArrayList<>();
    PieDataSet dataSet = new PieDataSet(macronutrients, "");
    PieData data = new PieData(dataSet);

    // newInstance constructor for creating fragment with arguments
    public static DashboardFragment newInstance(int page) {
        DashboardFragment fragmentFirst = new DashboardFragment();
        Bundle args = new Bundle();
        args.putInt("2", page);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: dashboard fragment created");
        page = getArguments().getInt("2", 0);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        TextView tvLabel = view.findViewById(R.id.dashboardTitleTextView);
        tvLabel.setText(R.string.dashboardTitle);
        donut = view.findViewById(R.id.dietDonut);
        donut.setNoDataText(EMPTY_DONUT);
        donut.getDescription().setEnabled(false);
        donut.setDrawEntryLabels(false);
        readFirebase();
        return view;
    }

    @Override
    public void readFirebase() {
        new FirebaseHelper().readDailyDonutValues(new FirebaseHelper.DailyDonutDataStatus() {
            @Override
            public void DailyDonutIsLoaded(MealPlanHelper.DonutChart donutChart) {
                macronutrients.clear();
                macronutrients.add(new PieEntry(donutChart.getCarbs().intValue(), "Carbs %"));
                macronutrients.add(new PieEntry(donutChart.getProtein().intValue(), "Protein %"));
                macronutrients.add(new PieEntry(donutChart.getFat().intValue(), "Fat %"));
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                donut.animateY(200);
                donut.setData(data);
                donut.invalidate();
            }

            @Override
            public void DailyDonutIsInserted() {

            }

            @Override
            public void DailyDonutIsUpdated(MealPlanHelper.DonutChart donutChart) {

            }

            @Override
            public void DailyDonutIsDeleted() {

            }
        });
    }
}
