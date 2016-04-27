package com.weiyu.handsomerunner.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.TabPageIndicator;
import com.weiyu.handsomerunner.R;
import com.weiyu.handsomerunner.adapter.ReportAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProgressReportFragment extends Fragment {
    private View view = null;
    private ViewPager vpProgressReport = null;
    private TabPageIndicator tpiNavigator = null;
    private DailyProgressReportFragment dailyProgressReportFragment = null;
    private WeeklyProgressReportFragment weeklyProgressReportFragment = null;

    private List<Fragment> fragments = null;
    public static String[] TITLES = new String[]{"Daily","Weekly"};

    public ProgressReportFragment() {
        // Required empty public constructor
    }


    public static ProgressReportFragment newInstance() {
        ProgressReportFragment fragment = new ProgressReportFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_progress_report, container, false);
        initView();
        initWidgets();
        return view;
    }

    private void initView() {
        tpiNavigator = (TabPageIndicator) view.findViewById(R.id.tpi_navigator);
        vpProgressReport = (ViewPager) view.findViewById(R.id.vp_progress_report);
    }


    /**
     * initiate a daily fragment and weekly fragment
     */
    private void initWidgets() {
        fragments = new ArrayList<>();
        if (null == dailyProgressReportFragment) {
            dailyProgressReportFragment = DailyProgressReportFragment.newInstance();
        }
        if (null == weeklyProgressReportFragment) {
            weeklyProgressReportFragment = WeeklyProgressReportFragment.newInstance();
        }
        fragments.add(dailyProgressReportFragment);
        fragments.add(weeklyProgressReportFragment);
        ReportAdapter adapter = new ReportAdapter(getChildFragmentManager(),fragments,TITLES);
        vpProgressReport.setAdapter(adapter);
        tpiNavigator.setViewPager(vpProgressReport);
    }

}
