package com.weiyu.handsomerunner.test;
import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class BingSearch {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //--------------------------------------Bing search------------------------------
        String searchText = "bread";
        searchText = searchText.replaceAll(" ", "%20");
//        String customerId = "0d41385d-6eb6-4857-9cd0-3c29c1a7ec52";
        String accountKey = "tZV9cZdEHd9Ww5gru7tAh1vM2wqAqdzblS3pHEjqgMw";

        byte[] accountKeyBytes = Base64.encodeBase64((accountKey + ":" + accountKey).getBytes());
        String accountKeyEnc = new String(accountKeyBytes);
        URL url;
        try {
//            &Sources=%27image%27
            String path = "https://api.datamarket.azure.com/Bing/Search/Web?Query=%27" + searchText + "%27&$top=10&$format=JSON&Market=%27en-us%27";
            System.out.println("path:" + path);
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
//            conn.setRequestProperty("Accept", "application/json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
//            BufferedReader br = new BufferedReader(new InputStreamReader(
//                    (conn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            System.out.println("Output from Server .... \n");
            char[] buffer = new char[4096];
            while ((output = reader.readLine()) != null) {
                sb.append(output);
                //text.append(link + "\n\n\n");//Will print the google search links
                //}
            }

            conn.disconnect();

            System.out.println(sb.toString());
/*
            int find = sb.indexOf("<d:Description");
            int total = find + 1000;
            System.out.println("Find index: " + find);
            System.out.println("Total index: " + total);
            sb.getChars(find + 35, total, buffer, 0);
            String str = new String(buffer);

            int find2 = str.indexOf("</d:Description>");
            int total2 = find2 + 400;
            System.out.println("Find index: " + find);
            System.out.println("Total index: " + total);
            char[] buffer2 = new char[1024];
            str.getChars(0, find2, buffer2, 0);
            String str2 = new String(buffer2);
            str2 = Jsoup.parse(str2).text();
            System.out.println(str2);*/
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

}
