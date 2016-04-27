package com.weiyu.handsomerunner.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.R;
import com.weiyu.handsomerunner.db.DBStepsHandler;
import com.weiyu.handsomerunner.domain.Steps;
import com.weiyu.handsomerunner.formatter.MyMarkerView;
import com.weiyu.handsomerunner.formatter.MyYAxisValueFormatter;
import com.weiyu.handsomerunner.service.GoaledCalorieService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DailyProgressReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyProgressReportFragment extends Fragment implements View.OnClickListener{
    private View view = null;
    private BarChart mChart = null;
    private LineChart lineChart = null;
    protected String[] mMonths = new String[]{
            "Consumed Calorie", "Burned Calorie"
    };

    public static final int[] JOYFUL_COLORS = {
            Color.rgb(217, 80, 138), Color.rgb(254, 149, 7)
    };


    private LinearLayout llCanlendar = null;
    private CalendarView cvCalendar = null;
    private ScrollView svGraphs = null;
    private TextView tvGivenDay = null;
    public DailyProgressReportFragment() {
        // Required empty public constructor
    }

    public static DailyProgressReportFragment newInstance() {
        DailyProgressReportFragment fragment = new DailyProgressReportFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_daily_progress_report, container, false);
        initView();
        initData();
        initEvent();
        return view;
    }

    private void initView() {
        mChart = (BarChart) view.findViewById(R.id.barChart);
        lineChart = (LineChart) view.findViewById(R.id.lineChart);
        llCanlendar = (LinearLayout) view.findViewById(R.id.ll_calendar);
        cvCalendar = (CalendarView) view.findViewById(R.id.cv_calendar);
        svGraphs = (ScrollView) view.findViewById(R.id.sv_graphs);
        tvGivenDay = (TextView) view.findViewById(R.id.tv_given_day);
    }

    private void initData() {
        /**
         * get consumed calorie and burned calorie
         */
        String today = Config.today();
        tvGivenDay.setText(today);

        showGraphs(today);
    }


    /**
     * show bar graph and line graph
     */
    private void showGraphs(String date) {
        GoaledCalorieService goaledCalorieService = new GoaledCalorieService();
        goaledCalorieService.getConsumedAndBurnedCalories(Config.getUserName(getActivity()), date, new GoaledCalorieService.GoaledCalorieCallback() {
            @Override
            public void onSuccess(Object obj) {
                String result = (String) obj;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String consumedCalories = jsonObject.getString("consumedCalories");
                    String burnedCalories = jsonObject.getString("burnedCalories");
//                    System.out.println(consumedCalories + ":" + burnedCalories);
                    DecimalFormat df = new DecimalFormat("0.00");
                    consumedCalories = df.format(Double.parseDouble(consumedCalories));
                    burnedCalories = df.format(Double.parseDouble(burnedCalories));
                    initBarChart(consumedCalories,burnedCalories);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail() {

            }

            @Override
            public void onEmpty() {

            }
        });

        /**
         * get today's total steps
         */
        DBStepsHandler dbStepsHandler = new DBStepsHandler(getActivity());
        List<Steps> steps = dbStepsHandler.queryStepsByTime(date);
        initLineChart(steps);
    }




    private void initEvent() {
        llCanlendar.setOnClickListener(this);
        cvCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = year + "-";
                if(month < 10){
                    date += "0" + (month + 1);
                }else{
                    date += month + 1;
                }

                if(dayOfMonth < 10){
                    date += "-0" + dayOfMonth;
                }else{
                    date += "-" + dayOfMonth;
                }
//                date = year + "-" + (month + 1) + "-" + dayOfMonth;
                tvGivenDay.setText(date);
                System.out.println("date:" + date);
                svGraphs.setVisibility(View.VISIBLE);
                cvCalendar.setVisibility(View.GONE);
                showGraphs(date);
            }
        });

    }


    /**
     *
     * @param steps
     */
    private void initLineChart(List<Steps> steps) {
        lineChart.setDrawGridBackground(false);

        // no description text
        lineChart.setDescription("");
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        lineChart.setTouchEnabled(true);

        // enable scaling and dragging
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(false);

        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);

        // set the marker to the chart
        lineChart.setMarkerView(mv);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(1);
        YAxisValueFormatter custom = new MyYAxisValueFormatter();

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

//        lineChart.getAxisRight().setEnabled(false);
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinValue(0f);


        Legend legend = lineChart.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setFormSize(9f);
        legend.setTextSize(11f);
        legend.setXEntrySpace(4f);


        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for(int i = 0;i < steps.size();i++){
            Steps step = steps.get(i);

            String updateTime = step.getUpdateTime();
            System.out.println(updateTime);
            updateTime = updateTime.substring(updateTime.indexOf(" "));
            xVals.add(updateTime);
            yVals.add(new Entry(step.getSteps(),i));
        }


        LineDataSet set1 = new LineDataSet(yVals, "Steps");
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(xVals, dataSets);
        lineChart.setData(data);
        lineChart.invalidate();
    }


    /**
     * init bar chart values
     * @param consumedCalories
     * @param burnedCalories
     */
    private void initBarChart(String consumedCalories, String burnedCalories) {
        float consumedCalorie = Float.parseFloat(consumedCalories);
        float burnedCalorie = Float.parseFloat(burnedCalories);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.setDescription("");
        mChart.setTouchEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(1);


        YAxisValueFormatter custom = new MyYAxisValueFormatter();

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)


        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinValue(0f);
//        mChart.getAxisRight().setEnabled(false);

        Legend legend = mChart.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setFormSize(9f);
        legend.setTextSize(11f);
        legend.setXEntrySpace(4f);
        legend.setExtra(JOYFUL_COLORS, new String[] { "Consumed Calorie",
         "Burned Calorie"});
        legend.setCustom(JOYFUL_COLORS, new String[] { "Consumed Calorie",
         "Burned Calorie"});
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();


        xVals.add(mMonths[0]);
        xVals.add(mMonths[1]);


        yVals1.add(new BarEntry(consumedCalorie,0));
        yVals1.add(new BarEntry(burnedCalorie,1));


        BarDataSet set1;

        set1 = new BarDataSet(yVals1, "DataSet");
        set1.setBarSpacePercent(35f);
        set1.setColors(JOYFUL_COLORS);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);

        mChart.setData(data);
        mChart.invalidate();

    }


    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_calendar:
                cvCalendar.setVisibility(View.VISIBLE);
                svGraphs.setVisibility(View.GONE);
                break;
        }
    }
}
