package com.example.android.quakereport.Object;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rykuno on 7/26/16.
 */
public class Earthquake {
    private double mMagnitude;
    private String mLocation;
    private String mUrl;
    private long mTimeInMilliseconds;

    public Earthquake(double magnitude, String location, String url, long timeInMilliseconds) {
        mMagnitude = magnitude;
        mLocation = location;
        mUrl = url;
        mTimeInMilliseconds = timeInMilliseconds;
    }

    public double getMagnitude() {
        return mMagnitude;
    }

    public String  getFormattedMagnitude() {
        DecimalFormat formatter = new DecimalFormat("0.0");
        String  formattedMagnitude = formatter.format(mMagnitude);
        return formattedMagnitude;
    }

    public String getLocation() {
        if (mLocation.contains("of")) {
            int loc = mLocation.indexOf("of") + 2;
            String locationBearing = mLocation.substring(loc, mLocation.length());
            return locationBearing;
        }
        else {
            return mLocation;
        }
    }

    public String getLocationBearing(){
        if (mLocation.contains("of")) {
            int loc = mLocation.indexOf("of") + 2;
            String locationBearing = mLocation.substring(0, loc);
            return locationBearing;
        }
        else{
            return "Near the";
        }
    }

    public String getFormatDate() {
        Date dateObject = new Date(mTimeInMilliseconds);
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    public String getFormatTime() {
        Date timeObject = new Date(mTimeInMilliseconds);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(timeObject);
    }

    public String getUrl() {
        return mUrl;
    }
}
