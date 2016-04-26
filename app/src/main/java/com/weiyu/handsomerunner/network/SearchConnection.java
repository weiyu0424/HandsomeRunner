package com.weiyu.handsomerunner.network;


import android.os.AsyncTask;
import android.text.TextUtils;

import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Sam on 4/19/2016.
 */
public class SearchConnection {

    public SearchConnection(final String content, final int length, final SearchMethod method, final SearchCallback callback){
        new AsyncTask<Void,Void,String>(){

            @Override
            protected String doInBackground(Void... params) {
                String searchContent = content;
                searchContent = searchContent.replaceAll(" ", "%20");
                String accountKey = "tZV9cZdEHd9Ww5gru7tAh1vM2wqAqdzblS3pHEjqgMw";
                HttpURLConnection conn = null;
                byte[] accountKeyBytes = Base64.encodeBase64((accountKey + ":" + accountKey).getBytes());
                String accountKeyEnc = new String(accountKeyBytes);
                String parameters = "%27&$top=" + length + "&$format=JSON&Market=%27en-us%27";
                String path = "https://api.datamarket.azure.com/Bing/Search/";
                switch (method){
                    case WEB:
                        path += "Web";
                        break;
                    case IMAGE:
                        path += "Image";
                        break;
                }

                path += "?Query=%27" + searchContent + parameters;

                try {
                    System.out.println("path:" + path);
                    URL url = new URL(path);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    System.out.println("Output from Server .... \n");

                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    System.out.println(sb.toString());
                    return sb.toString();

                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(conn != null){
                        conn.disconnect();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(!TextUtils.isEmpty(result)){
                    if(callback != null){
                        callback.onSuccess(result);
                    }
                }else{
                    if(callback != null){
                        callback.onFail();
                    }
                }
            }
        }.execute();

    }


    /**
     * a callback which can return the result back
     */
    public interface SearchCallback{
        void onSuccess(Object obj);
        void onFail();
    }
}
