package com.weiyu.handsomerunner.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Sam on 4/14/2016.
 */
public class NetworkUtils {
    private static ConnectivityManager cm = null;


    /**
     * judge that the current network is available or not
     * @param context:the current environment
     * @return: true represent the network is available and false means the network is not available
     */
    public static boolean isNetworkAvailable(Context context) {
        if(null == cm) {
            cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    /**
     * judge that the current network is WIFI or not
     * @param context: the current environment
     * @return: true: is WIFI; false: is not WIFI
     */
    public static boolean isWifi(Context context){
        if(null == cm) {
            cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
    }


    /**
     * judge that the current network is mobile data flow or not
     * @param context: the current environment
     * @return: true: is mobile data; false: is not mobile data
     */
    public static boolean isMobileData(Context context){
        if(null == cm) {
            cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
    }
}
