package com.kavi.droid.lowjuicelocation.models;

/**
 * Created by kavi707 on 2/11/18.
 * @author Kavimal Wijewardana <kavi707@gmail.com>
 */

public class Location {

    private long longitude;
    private long latitude;

    public Location(long longitude, long latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public long getLatitude() {
        return latitude;
    }
}
