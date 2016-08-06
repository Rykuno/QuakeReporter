package com.example.android.quakereport.Loaders;


import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.quakereport.Object.Earthquake;
import com.example.android.quakereport.Parsers.NetworkUtils;

import java.util.List;

/**
 * Loads a list of earthquakes by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {


    private static final String LOG_TAG = EarthquakeLoader.class.getName();
    private String mUrl;


    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Earthquake> earthquakes = NetworkUtils.fetchEarthquakeData(mUrl);
        return earthquakes;
    }


}