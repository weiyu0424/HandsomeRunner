package com.weiyu.handsomerunner.service;

import com.weiyu.handsomerunner.domain.Bing;
import com.weiyu.handsomerunner.network.SearchConnection;
import com.weiyu.handsomerunner.network.SearchMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sam on 4/19/2016.
 */
public class SearchService {


    /**
     * set from bing search engine with a given content, and return the results through callback
     * @param content: search content
     * @param method: search form, it has two forms, WEB or IMAGE
     * @param callback:the callback interface, which can be used to deliver the results to the caller
     */
    public void search(String content, int length, final SearchMethod method, final SearchSearviceCallback callback){
        new SearchConnection(content, length, method, new SearchConnection.SearchCallback() {
            @Override
            public void onSuccess(Object obj) {
                String result = (String) obj;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject searches = jsonObject.getJSONObject("d");

                    JSONArray results = searches.getJSONArray("results");
                    JSONObject object = results.getJSONObject(0);
                    Bing bing = new Bing();
                    switch (method){
                        case WEB:
                            String title = object.getString("Title");
                            String description = object.getString("Description");
                            String displayUrl = object.getString("DisplayUrl");
                            bing.setTitle(title);
                            bing.setDescription(description);
                            bing.setDisplayUrl(displayUrl);
                            break;

                        case IMAGE:
                            JSONObject thumbnail = object.getJSONObject("Thumbnail");
                            String mediaUrl = thumbnail.getString("MediaUrl");
                            bing.setMediaUrl(mediaUrl);
                            break;
                    }

                    if(callback != null){
                        callback.onSuccess(bing);
                    }

                    //System.out.println("length:" + results.length());
                } catch (JSONException e) {
                    e.printStackTrace();
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
        });
    }

    public interface SearchSearviceCallback{
        void onSuccess(Object obj);
        void onFail();
    }
}
