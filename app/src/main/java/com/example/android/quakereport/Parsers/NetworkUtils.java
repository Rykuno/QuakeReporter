package com.example.android.quakereport.Parsers;

import android.text.TextUtils;
import android.util.Log;
import android.util.StringBuilderPrinter;

import com.example.android.quakereport.Object.Earthquake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rykuno on 8/4/16.
 */
public class NetworkUtils {

    public static List<Earthquake> fetchEarthquakeData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;

        try{
            Thread.sleep(2000);
            jsonResponse = makeHttpRequest(url);
            Log.v(NetworkUtils.class.getSimpleName(), "MAKING NETWORK CALL");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Earthquake> earthquakeList = new ArrayList<>(extractFeatureFromJson(jsonResponse));
        return  earthquakeList;
    }

    private static List<Earthquake> extractFeatureFromJson(String jsonResponse) {
        List<Earthquake> earthquakeList = new ArrayList<>();

        if (TextUtils.isEmpty(jsonResponse)){
            return null;
        }

        try{
            JSONObject rootJsonResponse = new JSONObject(jsonResponse);
            JSONArray earthquakeArray = rootJsonResponse.getJSONArray("features");
            for (int i =0; i<earthquakeArray.length(); i++){
                JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
                JSONObject properties = currentEarthquake.getJSONObject("properties");
                double mag = properties.getDouble("mag");
                String location = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");

                earthquakeList.add(new Earthquake(mag, location, url, time));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return earthquakeList;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createUrl(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
