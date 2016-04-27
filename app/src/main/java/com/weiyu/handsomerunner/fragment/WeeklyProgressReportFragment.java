package com.weiyu.handsomerunner.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
import com.weiyu.handsomerunner.domain.Report;
import com.weiyu.handsomerunner.domain.Steps;
import com.weiyu.handsomerunner.formatter.MyMarkerView;
import com.weiyu.handsomerunner.formatter.MyYAxisValueFormatter;
import com.weiyu.handsomerunner.service.ReportService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeeklyProgressReportFragment extends Fragment implements View.OnClickListener {

    private BarChart barChart = null;
    private LineChart lineChart = null;

    private View view = null;
    public static final int[] JOYFUL_COLORS = {
            Color.rgb(217, 80, 138), Color.rgb(254, 149, 7)
    };
    private LinearLayout llCanlendar = null;
    private CalendarView cvCalendar = null;
    private ScrollView svGraphs = null;
    private TextView tvStartDay = null;
    private TextView tvEndDay = null;

    public WeeklyProgressReportFragment() {
        // Required empty public constructor
    }

    public static WeeklyProgressReportFragment newInstance() {
        WeeklyProgressReportFragment fragment = new WeeklyProgressReportFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weekly_progress_report, container, false);
        initView();
        initData();
        initEvent();
        return view;
    }

    private void initView() {
        barChart = (BarChart) view.findViewById(R.id.barChart);
        lineChart = (LineChart) view.findViewById(R.id.lineChart);
        llCanlendar = (LinearLayout) view.findViewById(R.id.ll_calendar);
        svGraphs = (ScrollView) view.findViewById(R.id.sv_graphs);
        tvStartDay = (TextView) view.findViewById(R.id.tv_start_day);
        tvEndDay = (TextView) view.findViewById(R.id.tv_end_day);
        cvCalendar = (CalendarView) view.findViewById(R.id.cv_calendar);
    }

    private void initData() {
        String today = Config.today();
        String startDay = sevenDaysBefore();
        tvStartDay.setText(startDay);
        tvEndDay.setText(today);
        getReportRecords(startDay, today);
    }


    private void initEvent() {
        tvStartDay.setOnClickListener(this);
        tvEndDay.setOnClickListener(this);
    }


    /**
     * get report records within a time period
     * @param today
     * @param startDay
     */
    private void getReportRecords(String startDay, String today) {
        new ReportService().getReportRecordsDuringTime(Config.getUserName(getActivity()), startDay, today, new ReportService.ReportCallback() {
            @Override
            public void onSuccess(Object result) {
                List<Report> reports = (List<Report>) result;
                initBarGraph(reports);
                initLineGraph(reports);
            }

            @Override
            public void onFail() {
                Config.toast(getActivity(), "Oops, Failed to retrieve report records");
            }
        });
    }


    /**
     * get a given day which is 7 days before today
     * @return: the given day
     */
    private String sevenDaysBefore() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        long start = System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 7;
        Date end = new Date(start);
        String endDay = format.format(end);
        return endDay;
    }

    /**
     * create relevant line graph and bar graph
     *
     * @param reports
     */
    private void initBarGraph(List<Report> reports) {

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setDescription("");
        barChart.setTouchEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false);
        barChart.setScaleEnabled(false);
        barChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(1);

        YAxisValueFormatter custom = new MyYAxisValueFormatter();

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinValue(0f);

        barChart.getAxisRight().setEnabled(false);
        Legend legend = barChart.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setFormSize(9f);
        legend.setTextSize(11f);
        legend.setXEntrySpace(4f);
        legend.setExtra(JOYFUL_COLORS, new String[]{"Consumed Calorie",
                "Burned Calorie"});
        legend.setCustom(JOYFUL_COLORS, new String[]{"Consumed Calorie",
                "Burned Calorie"});

        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<>();


        for (int i = 0; i < reports.size(); i++) {
            Report report = reports.get(i);
            xVals.add(report.getUpdateTime());

            yVals1.add(new BarEntry((float) report.getConsumedCalories(), i));
            yVals2.add(new BarEntry((float) report.getBurnedCalories(), i));
        }


        BarDataSet set1 = new BarDataSet(yVals1, "Consumed Calories");
//        set1.setBarSpacePercent(35f);
        set1.setColor(JOYFUL_COLORS[0]);


        BarDataSet set2 = new BarDataSet(yVals2, "burned Calories");
//        set2.setBarSpacePercent(35f);
        set2.setColor(JOYFUL_COLORS[1]);


        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);

        barChart.setData(data);
        barChart.invalidate();
    }


    private void initLineGraph(List<Report> reports) {
        lineChart.setDrawGridBackground(false);

        // no description text
        lineChart.setDescription("");
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        lineChart.setTouchEnabled(true);

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
        for (int i = 0; i < reports.size(); i++) {
            Report report = reports.get(i);

            xVals.add(report.getUpdateTime());
            yVals.add(new Entry((float) report.getSteps(), i));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start_day:
                cvCalendar.setVisibility(View.VISIBLE);
                svGraphs.setVisibility(View.GONE);
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
                        tvStartDay.setText(date);
                        cvCalendar.setVisibility(View.GONE);
                        svGraphs.setVisibility(View.VISIBLE);
                        getReportRecords(date.toString(),tvEndDay.getText().toString());
                    }
                });
                break;
            case R.id.tv_end_day:
                cvCalendar.setVisibility(View.VISIBLE);
                svGraphs.setVisibility(View.GONE);
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
                        tvEndDay.setText(date);
                        cvCalendar.setVisibility(View.GONE);
                        svGraphs.setVisibility(View.VISIBLE);
                        getReportRecords(tvStartDay.getText().toString(),date);
                    }
                });
                break;
        }
    }
}
