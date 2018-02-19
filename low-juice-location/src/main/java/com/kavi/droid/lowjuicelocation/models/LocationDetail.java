package com.kavi.droid.lowjuicelocation.models;

import android.support.annotation.NonNull;

/**
 * Created by kavi707 on 2/11/18.
 * @author Kavimal Wijewardana <kavi707@gmail.com>
 */

public class LocationDetail {

    private Location location;
    private String address;
    private double accuracy;

    public LocationDetail(@NonNull Location location,
                          @NonNull String address,
                          double accuracy) {
        this.location = location;
        this.address = address;
        this.accuracy = accuracy;
    }

    public Location getLocation() {
        return location;
    }

    public String getAddress() {
        return address;
    }

    public double getAccuracy() {
        return accuracy;
    }
}
