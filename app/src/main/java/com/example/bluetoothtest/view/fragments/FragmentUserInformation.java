package com.example.bluetoothtest.view.fragments;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.bluetoothtest.utility.WindowSetting;
import com.example.bluetoothtest.view.fragments.FragmentUserInformationArgs;
import com.example.bluetoothtest.view.fragments.FragmentUserInformationDirections;
import com.example.bluetoothtest.utility.ProfileHelper;
import com.example.bluetoothtest.R;
import com.example.bluetoothtest.model.entities.users.User;
import com.example.bluetoothtest.view_model.UserViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Random;

public class FragmentUserInformation extends Fragment {

    TextView playerName;
    TextView reactionTime;
    TextView parentName;
    ImageView profileImage;
    ImageView getBack;
    LineChart lineChart;
    CardView cardViewContainer;
    ArrayList<Entry> chartData = new ArrayList<>();
    WindowSetting windowSetting;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_information, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initGraph();


        UserViewModel userViewModel = new ViewModelProvider(this).
                get(UserViewModel.class);

        FragmentUserInformationArgs arguments = FragmentUserInformationArgs.fromBundle(getArguments());

        String personName = arguments.getPersonName();

        User user = userViewModel.getUser(personName);

        playerName.setText(user.name);
        reactionTime.setText(String.valueOf(user.reactionTime));

        String profilePath = user.profilePath;

        ProfileHelper.getImage(profilePath, profileImage);


        getBack.setOnClickListener(m -> Navigation.findNavController(m).navigateUp());


        profileImage.setOnClickListener(v -> {
            Navigation.findNavController(v).
                    navigate(FragmentUserInformationDirections
                            .actionFUserInformationToFragmentEditProfile(personName));
        });

    }


    private void configureDataSetLineProperties(LineDataSet dataSet) {
        dataSet.setColor(getContext().getResources().getColor(R.color.colorAccent));
        dataSet.setLineWidth(3f);
        dataSet.setDrawCircles(false);
        dataSet.setDrawFilled(true);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setCubicIntensity(0.1f);
        dataSet.setDrawValues(false);
    }

    @Override
    public void onStart() {
        super.onStart();

        windowSetting = new WindowSetting(getActivity().getWindow());
        windowSetting.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.colorBackgroundDarker));
    }

    private void initView(View view) {
        playerName = view.findViewById(R.id.player_name);
        parentName = view.findViewById(R.id.parent_name);
        reactionTime = view.findViewById(R.id.reaction_time);
        profileImage = view.findViewById(R.id.image_user_profile);
        getBack = view.findViewById(R.id.get_back_icon);
        lineChart = view.findViewById(R.id.chart_user_reaction);
        cardViewContainer = view.findViewById(R.id.user_profile);
    }

    @Override
    public void onResume() {
        super.onResume();

        chartData.clear();
        chartData.add(new Entry(10, 0));
        for (int i = 0; i < 10; i++) {
            chartData.add(new Entry(chartData.size() == 0 ? 0 : chartData.get(chartData.size() - 1).getX() + 10, new Random().nextInt(50)));
        }

        LineDataSet dataSet = new LineDataSet(chartData, "Reaction Time");
        configureDataSetLineProperties(dataSet);

        dataSet.setFillColor(getContext().getResources().getColor(R.color.colorAccentC));
        LineData dataToShow = new LineData(dataSet);

        lineChart.setData(dataToShow);
        chartRefresh(dataSet, dataToShow);

    }

    private void initGraph() {
        Description description = new Description();
        description.setEnabled(false);
        lineChart.setDescription(description);

        //  lineChart.setDrawBorders(true);
        //  lineChart.setBorderWidth(2.5f);
        //  lineChart.setBorderColor(getContext().getResources().getColor(R.color.colorAccentA));

        lineChart.setDrawGridBackground(false);
        lineChart.setBorderWidth(2f);
        lineChart.setTouchEnabled(false);

        final Legend legend = lineChart.getLegend();
        legend.setTextColor(Color.WHITE);
        legend.setTextSize(19.5f);

        YAxis yAxisl = lineChart.getAxisLeft();
        yAxisl.setDrawAxisLine(false);
        yAxisl.setDrawLabels(true);
        yAxisl.setTextSize(14);
        yAxisl.setTextColor(Color.WHITE);

        yAxisl.setLabelCount(4, true);
        yAxisl.setDrawGridLines(false);
        YAxis yAxis = lineChart.getAxisRight();
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawGridLines(false);
        yAxis.setDrawLabels(false);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawAxisLine(false);
        xAxis.setTextSize(14);
        xAxis.setDrawLabels(false);
        xAxis.setDrawGridLines(false);
    }

    private void chartRefresh(LineDataSet dataset, LineData data) {
        dataset.notifyDataSetChanged();
        data.notifyDataChanged();
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }


}
