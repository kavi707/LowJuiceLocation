package com.kavi.droid.lowjuicelocation.services.connections;

import android.util.Log;

import com.kavi.droid.lowjuicelocation.models.Location;
import com.kavi.droid.lowjuicelocation.models.LocationDetail;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kavi707 on 2/11/18.
 * @author Kavimal Wijewardana <kavi707@gmail.com>
 */

public class Utils {

    private static Utils utils;

    public static Utils getInstance() {
        if (utils == null)
            utils = new Utils();

        return utils;
    }

    public LocationDetail getLocationDetails (JSONObject response) {

        LocationDetail locationDetail = null;
        try {
            if (response != null) {
                Location location = new Location(response.getLong("lon"), response.getLong("lat"));
                locationDetail = new LocationDetail(location,
                        response.getString("address"),
                        response.getDouble("accuracy"));
            }
        } catch (JSONException e) {
            Log.e("", e.toString());
        }

        return locationDetail;
    }
}
