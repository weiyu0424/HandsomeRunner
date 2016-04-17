package com.weiyu.handsomerunner.network;

import android.os.AsyncTask;

import com.weiyu.handsomerunner.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Sam on 2016/4/12.
 */
public class NetConnection {

    private URLConnection conn = null;


    /**
     * in this class, I have encapsulated a fundamental function which can do the network operations with the given parameters
     * @param url: the server address
     * @param method: POST or GET method
     * @param callback: the result will be delivered through this interface
     * @param params: the relevant parameters which need to be passed to the server api
     */
    public NetConnection(final String url, final HttpMethod method, final ConnectionCallback callback, final String ... params){
//        final String myurl = "http://172.16.120.11:8080/CaloriesServer/webresources/user/register/";
        //open a child thread to do the network communication
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... param) {
                StringBuffer sb = new StringBuffer();
                BufferedWriter writer = null;
                BufferedReader reader = null;
                /*for (int i = 0; i < params.length; i += 2) {
                    sb.append(params[i]).append("=").append(params[i + 1]).append("&");
                }*/

                for(int i = 0;i < params.length; i++){
                    sb.append("/").append(params[i]);
                }

                try {
                    switch (method) {
                        case POST:
                            //if the network communication is POST, do the following operations
                            conn = new URL(url).openConnection();
                            conn.setDoOutput(true);
                            writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), Config.CHARSET));
                            writer.write(sb.toString());
                            writer.flush();
                            break;
                        case GET:
                            //if the network communication is GET, do the following operations
                            String path = url + sb.toString();
                            System.out.println("path:" + path);
                            conn = new URL(path).openConnection();
                            break;
                    }

                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),Config.CHARSET));
                    String line = null;
                    StringBuffer result = new StringBuffer();
                    while((line = reader.readLine()) != null){
                        result.append(line);
                    }
                    return result.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                    if(callback != null){
                        callback.onFail();
                    }
                }finally {
                    if(reader != null){
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if(writer != null){
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            }



            @Override
            protected void onPostExecute(String content) {
                super.onPostExecute(content);
                if(content != null){
                    if(callback != null){
                        callback.onSuccess(content);
                    }
                }else{
                    if(callback != null){
                        callback.onFail();
                    }
                }
            }
        }.execute();

    }


    public interface ConnectionCallback{
        void onSuccess(String result);
        void onFail();
    }
}
