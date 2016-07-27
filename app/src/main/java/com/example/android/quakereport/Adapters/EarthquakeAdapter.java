package com.example.android.quakereport.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.quakereport.Object.Earthquake;
import com.example.android.quakereport.R;

import java.util.List;

/**
 * Created by rykuno on 7/26/16.
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Context context, List<Earthquake> objects) {
        super(context, 0, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);
        }

        Earthquake currentEarthquake = getItem(position);
        TextView magnitude = (TextView) listItemView.findViewById(R.id.magnitude);
        TextView locationBearing = (TextView) listItemView.findViewById(R.id.locationBearing);
        TextView location = (TextView) listItemView.findViewById(R.id.location);
        TextView date = (TextView) listItemView.findViewById(R.id.date);
        TextView time = (TextView) listItemView.findViewById(R.id.time);

        magnitude.setText(currentEarthquake.getMagnitude());
        locationBearing.setText(currentEarthquake.getLocationBearing());
        location.setText(currentEarthquake.getLocation());
        date.setText(currentEarthquake.getFormatDate());
        time.setText(currentEarthquake.getFormatTime());

        return  listItemView;
    }
}
