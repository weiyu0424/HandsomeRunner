package com.weiyu.handsomerunner.service;

import android.text.TextUtils;

import com.weiyu.handsomerunner.Config;
import com.weiyu.handsomerunner.domain.Food;
import com.weiyu.handsomerunner.domain.Report;
import com.weiyu.handsomerunner.network.HttpMethod;
import com.weiyu.handsomerunner.network.NetConnection;
import com.weiyu.handsomerunner.utils.FoodUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 4/27/2016.
 */
public class ReportService {


    public void getReportRecordsDuringTime(String userName, String startTime, String endTime, final ReportCallback callback){
        new NetConnection(Config.QUERY_REPORT_RECORDS, HttpMethod.GET, new NetConnection.ConnectionCallback() {
            @Override
            public void onSuccess(String result) {
                if(!TextUtils.isEmpty(result)){
                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        if(jsonArray.length() != 0){
                            List<Report> reports = new ArrayList<>();
                            for(int i = 0;i < jsonArray.length();i++){
                                Report report = new Report();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                report.setGoaledCalories(jsonObject.getDouble("goaledCalories"));
                                report.setConsumedCalories(jsonObject.getDouble("consumedCalories"));
                                report.setBurnedCalories(jsonObject.getDouble("burnedCalories"));
                                report.setSteps(jsonObject.getDouble("steps"));
                                String updateTime = jsonObject.getString("time");
                                updateTime = updateTime.substring(0,updateTime.indexOf("T"));
                                report.setUpdateTime(updateTime);
                                reports.add(report);
                            }

                            if(callback != null){
                                callback.onSuccess(reports);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if(callback != null){
                            callback.onFail();
                        }
                    }

                }else{
                    if(callback != null){
                        callback.onFail();
                    }
                }
            }

            @Override
            public void onFail() {
                if(callback != null){
                    callback.onFail();
                }
            }
        },userName,startTime,endTime);
    }

    /**
     * a customized callback which can be used to pass result;
     */
    public interface ReportCallback{
        void onSuccess(Object result);
        void onFail();
    }
}
