package com.example.CalPro.Fragments.FragmentProgress;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.CalPro.Activities.MainActivity;
import com.example.CalPro.R;
import com.example.CalPro.Utils.CONSTANTS;
import com.example.CalPro.Utils.HelperClasses.FirebaseHelper;
import com.example.CalPro.Utils.HelperClasses.WeightProgressHelper;
import com.example.CalPro.Utils.Interfaces.FirebaseInterface;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.CalPro.Utils.CONSTANTS.EMPTY_WEIGHT_CHART;

public class ProgressFragment extends Fragment implements FirebaseInterface {
    private static final String TAG = "ProgressFragment";
    private int page;
    private LineChart progressChart;
    private LineDataSet lineDataSet;
    private List<ILineDataSet> dataSets;
    private int year, month;
    private String monthName;

    // newInstance constructor for creating fragment with arguments
    public static ProgressFragment newInstance(int page) {
        ProgressFragment fragmentFirst = new ProgressFragment();
        Bundle args = new Bundle();
        args.putInt("3", page);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("3", 0);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress , container, false);
        ((MainActivity)getActivity()).setView(view);
        TextView tvLabel = view.findViewById(R.id.progressTitleTextView);
        tvLabel.setText(R.string.progressTitle);
        progressChart = view.findViewById(R.id.progressChart);
        progressChart.setNoDataText(EMPTY_WEIGHT_CHART);
        progressChart.getDescription().setEnabled(false);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        monthName = CONSTANTS.getMonthName(month);
        readFirebase();
        return view;
    }

    @Override
    public void readFirebase() {
        new FirebaseHelper().readWeightProgressChart(new FirebaseHelper.WeightChartDataStatus() {
            @Override
            public void WeightChartIsLoaded(List<Entry> entries) {
                if (entries.size() > 0) {
                    Log.d(TAG, "WeightChartIsLoaded: loading weight entries");
                    lineDataSet = new LineDataSet(entries, "Weight");
                    dataSets  = new ArrayList<>();
                    dataSets.add(lineDataSet);
                    LineData data = new LineData(dataSets);
                    progressChart.animateY(200);
                    progressChart.setData(data);
                    progressChart.invalidate();
                }
            }

            @Override
            public void WeightChartIsInserted() {

            }

            @Override
            public void WeightChartIsUpdated() {

            }

            @Override
            public void WeightChartIsDeleted() {

            }
        }, year, monthName);
    }
}
