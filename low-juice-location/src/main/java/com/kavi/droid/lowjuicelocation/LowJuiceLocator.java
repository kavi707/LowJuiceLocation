package com.kavi.droid.lowjuicelocation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;

import com.kavi.droid.lowjuicelocation.models.LocationDetail;
import com.kavi.droid.lowjuicelocation.models.NetworkCellInfo;
import com.kavi.droid.lowjuicelocation.models.NetworkOperatorInfo;
import com.kavi.droid.lowjuicelocation.services.connections.ApiCallResponseHandler;
import com.kavi.droid.lowjuicelocation.services.connections.ApiCalls;
import com.kavi.droid.lowjuicelocation.utils.LocatorUtils;


/**
 * Created by kavi707 on 2/11/18.
 * @author Kavimal Wijewardana <kavi707@gmail.com>
 */

public class LowJuiceLocator extends PhoneStateListener {

    private static LowJuiceLocator locator = null;
    private Context context;
    private OnLocationChangeListener onLocationChangeListener;
    private ApiCalls apiCalls = new ApiCalls();

    /**
     * Create new instance of the LowJuiceLocator
     * @param context Application/Activity context
     * @param onLocationChangeListener Callback method to onLocation change due to Network Cell change
     */
    private LowJuiceLocator(Context context, OnLocationChangeListener onLocationChangeListener) {
        this.context = context;
        this.onLocationChangeListener = onLocationChangeListener;
    }

    /**
     * Initiate LowJuiceLocator
     * @param context Application/Activity context
     * @param onLocationChangeListener Callback method to onLocation change due to Network Cell change
     * @return LowJuiceLocator object - instance of the LowJuiceLocator
     */
    public static LowJuiceLocator getInstance(Context context, OnLocationChangeListener onLocationChangeListener) {
        if (locator == null)
            locator = new LowJuiceLocator(context, onLocationChangeListener);

        return locator;
    }


    /**
     * Refresh & get location from Network Cell
     */
    @RequiresPermission(allOf = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION})
    public void refreshLocation() {
        NetworkOperatorInfo networkOperatorInfo = LocatorUtils.getInstance(context).getOperatorInfo();
        NetworkCellInfo networkCellInfo = LocatorUtils.getInstance(context).getCellInfo();

        getLocation(networkOperatorInfo, networkCellInfo);
    }


    @Override
    public void onCellLocationChanged(CellLocation location) {
        super.onCellLocationChanged(location);
        if (location != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                refreshLocation();
            }
        }
    }

    /**
     * Get Location from OpenCellId
     * @param networkOperatorInfo NetworkOperatorInfo object includes MCC, MNC, & network type data
     * @param networkCellInfo NetworkCellInfo object includes CellId & LAC data
     */
    private void getLocation(NetworkOperatorInfo networkOperatorInfo, NetworkCellInfo networkCellInfo) {
        apiCalls.getLocation(context, Constants.ASYNC_METHOD,
                networkOperatorInfo, networkCellInfo, new ApiCallResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, LocationDetail locationDetail) {
                        onLocationChangeListener.onLocationChange(locationDetail);
                    }
                });
    }
}
