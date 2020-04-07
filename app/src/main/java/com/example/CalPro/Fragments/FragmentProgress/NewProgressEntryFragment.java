package com.example.CalPro.Fragments.FragmentProgress;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.CalPro.Activities.MainActivity;
import com.example.CalPro.Fragments.FragmentPopups.NewWeightEntryFragment;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.CalPro.Utils.CONSTANTS.NEW_WEIGHT_ENTRY;

public class NewProgressEntryFragment extends Fragment implements FirebaseInterface {

    private static final String TAG = "NewProgressEntryFrag";
    private int page;
    private CalendarView calendarView;
    private View view;
    private LineChart progressChart;
    private LineDataSet lineDataSet;
    private List<ILineDataSet> dataSets;
    private String monthName;
    private int yearNumber;
    private int dayNumber;
    private boolean existingEntry;
    private int entryNumber;
    private Double weightEntry;
    private FloatingActionButton editEntryButton, removeEntryButton, addEntryButton;

    public static Fragment newInstance(int page) {
        NewProgressEntryFragment fragmentFirst = new NewProgressEntryFragment();
        Bundle args = new Bundle();
        args.putInt("1", page);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: new progress entry fragment created");
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("1", 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_progress_entry , container, false);
        editEntryButton = view.findViewById(R.id.editEntryButton);
        removeEntryButton = view.findViewById(R.id.removeEntryButton);
        addEntryButton = view.findViewById(R.id.addEntryButton);


        progressChart = ((MainActivity)getActivity()).getFragmentView().findViewById(R.id.progressChart);

        calendarView = view.findViewById(R.id.newEntryCalendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                editEntryButton.setVisibility(View.INVISIBLE);
                removeEntryButton.setVisibility(View.INVISIBLE);
                addEntryButton.setVisibility(View.INVISIBLE);
                setMonthName(monthName = CONSTANTS.getMonthName(month));
                setYearNumber(year);
                setDayNumber(day);
                Log.d(TAG, "onSelectedDayChange: " + year + " and month: " + monthName);
                readFirebase();
            }
        });

        editEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).addFragment(NEW_WEIGHT_ENTRY);
            }
        });

        removeEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFirebase();
            }
        });

        addEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).addFragment(NEW_WEIGHT_ENTRY);
            }
        });

        return view;
    }



    @Override
    public void readFirebase() {
        new FirebaseHelper().readWeightProgressChart(new FirebaseHelper.WeightChartDataStatus() {
            @SuppressLint("RestrictedApi")
            @Override
            public void WeightChartIsLoaded(List<Entry> entries) {
                if (entries.size() > 0) {
                    Log.d(TAG, "WeightChartIsLoaded: loading weight entries into chart");
                    lineDataSet = new LineDataSet(entries, "Weight");
                    dataSets  = new ArrayList<>();
                    dataSets.add(lineDataSet);
                    LineData data = new LineData(dataSets);
                    progressChart.animateY(800);
                    progressChart.setData(data);
                    progressChart.invalidate();

                    for (int i = 0; i < entries.size(); i++) {
                        if (entries.get(i).getX() == getDayNumber()) {
                            setExistingEntry(true);
                            break;
                        }
                        else {
                            setExistingEntry(false);
                        }
                    }
                }
                else {
                    progressChart.clear();
                    Log.d(TAG, "WeightChartIsLoaded: no entry found");
                    setExistingEntry(false);
                }

                if (isExistingEntry()) {
                    Log.d(TAG, "WeightChartIsLoaded: found existing entry, updating buttons");
                    editEntryButton.setVisibility(View.VISIBLE);
                    removeEntryButton.setVisibility(View.VISIBLE);
                    addEntryButton.setVisibility(View.INVISIBLE);
                }
                else {
                    editEntryButton.setVisibility(View.INVISIBLE);
                    removeEntryButton.setVisibility(View.INVISIBLE);
                    addEntryButton.setVisibility(View.VISIBLE);
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
        }, yearNumber, monthName);
    }

    public void updateFirebase() {
        new FirebaseHelper().updateWeightChart(new FirebaseHelper.WeightChartDataStatus() {
            @Override
            public void WeightChartIsLoaded(List<Entry> entries) {

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
        }, getYearNumber(), getMonthName());
    }

    public void deleteFirebase() {
        new FirebaseHelper().deleteWeightChartEntry(new FirebaseHelper.WeightChartDataStatus() {
            @Override
            public void WeightChartIsLoaded(List<Entry> entries) {

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
        }, getYearNumber(), getMonthName(), getDayNumber());
    }

    public void insertFirebase() {
        new FirebaseHelper().insertWeightChartEntry(new FirebaseHelper.WeightChartDataStatus() {
            @Override
            public void WeightChartIsLoaded(List<Entry> entries) {

            }

            @Override
            public void WeightChartIsInserted() {
                Log.d(TAG, "WeightChartIsInserted: inserted new entry");
            }

            @Override
            public void WeightChartIsUpdated() {

            }

            @Override
            public void WeightChartIsDeleted() {

            }
        }, getYearNumber(), getMonthName(), getDayNumber(), getWeightEntry());
    }

    //region getters & setters
    private void setYearNumber(int year) {
        yearNumber = year;
    }

    private int getYearNumber() {
        return yearNumber;
    }

    private void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    private String getMonthName() {
        return monthName;
    }

    private void setDayNumber(int day) {
        dayNumber = day;
    }

    private int getDayNumber() {
        return dayNumber;
    }


    public boolean isExistingEntry() {
        return existingEntry;
    }

    public void setExistingEntry(boolean existingEntry) {
        this.existingEntry = existingEntry;
    }

    public int getEntryNumber() {
        return entryNumber;
    }

    public void setEntryNumber(int entryNumber) {
        this.entryNumber = entryNumber;
    }
    public Double getWeightEntry() {
        return weightEntry;
    }

    public void setWeightEntry(Double weightEntry) {
        this.weightEntry = weightEntry;
    }
    //endregion
}
