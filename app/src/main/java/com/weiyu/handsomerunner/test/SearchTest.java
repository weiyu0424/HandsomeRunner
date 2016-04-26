package com.weiyu.handsomerunner.test;

import android.util.Base64;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Sam on 4/18/2016.
 */
public class SearchTest {
    public static void main(String[] args) {
        final String accountKey = "R9sUqOgNFwivbfuSwMAlWbCaPp3KWvR5CiwgFgCfQ4Q=";
        final String bingUrlPattern = "https://api.datamarket.azure.com/Bing/Search/Web?Query=%%27%s%%27&$format=JSON";
        try {
            final String query = URLEncoder.encode("'what is omonoia'", Charset.defaultCharset().name());
            final String bingUrl = String.format(bingUrlPattern, query);

            final String accountKeyEnc = Base64.encodeToString(accountKey.getBytes(), Base64.DEFAULT);

            final URL url = new URL(bingUrl);
            final URLConnection connection = url.openConnection();
            connection.setRequestProperty("Authorization", "Basic " + accountKeyEnc);

            final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            final StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            final JSONObject json = new JSONObject(response.toString());
            final JSONObject d = json.getJSONObject("d");
            final JSONArray results = d.getJSONArray("results");
            final int resultsLength = results.length();
            for (int i = 0; i < resultsLength; i++) {
                final JSONObject aResult = results.getJSONObject(i);
                System.out.println(aResult.get("Url"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
