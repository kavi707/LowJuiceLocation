package com.kavi.droid.lowjuicelocation.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;

import com.kavi.droid.lowjuicelocation.models.NetworkCellInfo;
import com.kavi.droid.lowjuicelocation.models.NetworkOperatorInfo;

/**
 * Created by kavi707 on 2/11/18.
 * @author Kavimal Wijewardana <kavi707@gmail.com>
 */

public class LocatorUtils {

    private static LocatorUtils locatorUtils;
    private Context context = null;

    public static LocatorUtils getInstance(Context context) {
        if (locatorUtils == null)
            locatorUtils = new LocatorUtils(context);

        return locatorUtils;
    }

    private LocatorUtils(Context context) {
        this.context = context;
    }

    /**
     * Retrieve network MCC, MNC and network type from TelephonyManager & create NetworkOperatorInfo model
     * @return NetworkOperatorInfo object - Includes MCC, MNC & NetworkType
     */
    public NetworkOperatorInfo getOperatorInfo() {
        NetworkOperatorInfo networkOperatorInfo = null;

        TelephonyManager telephonyManager = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

            String networkData = null;
            int networkType = 0;
            if (telephonyManager != null) {
                networkData = telephonyManager.getSimOperator();

                if (networkData == null)
                    networkData = telephonyManager.getNetworkOperator();

                networkType = telephonyManager.getNetworkType();

                networkOperatorInfo = new NetworkOperatorInfo(getMCC(networkData),
                        getMNC(networkData), getNetworkTypeFromCode(networkType));
            }
        }

        return networkOperatorInfo;
    }

    /**
     * Retrieve Network cell details using TelephonyManager & create NetworkCellInfo model
     * @return NetworkCellInfo object - Includes CellId, LAC
     */
    public NetworkCellInfo getCellInfo() {

        GsmCellLocation location;
        NetworkCellInfo networkCellInfo = null;

        TelephonyManager telephonyManager = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

            if (telephonyManager != null) {
                location = (GsmCellLocation) telephonyManager.getCellLocation();
                networkCellInfo = new NetworkCellInfo(location.getCid(), location.getLac());
            }
        }


        return networkCellInfo;
    }

    /**
     * Get the network type name from given network type
     * @param code NetworkType code return from TelephonyManager
     * @return String object - Name of the network type (gsm, cdma, utms, lte) & default case will be unknown
     */
    private String getNetworkTypeFromCode(int code) {
        String networkType = "";
        switch (code) {
            case 16:
                networkType = NetworkOperatorInfo.GSM_TYPE;
                break;
            case 4:
                networkType = NetworkOperatorInfo.CDMA_TYPE;
                break;
            case 3:
                networkType = NetworkOperatorInfo.UMTS_TYPE;
                break;
            case 13:
                networkType = NetworkOperatorInfo.LTE_TYPE;
                break;
            case 8:
                networkType = NetworkOperatorInfo.LTE_TYPE;
                break;
            default:
                networkType = NetworkOperatorInfo.UNKNOWN_TYPE;
                break;
        }
        return networkType;
    }

    private int getMCC(String networkData) {
        return Integer.parseInt(networkData.substring(0, 3));
    }

    private int getMNC(String networkData) {
        return Integer.parseInt(networkData.substring(3));
    }
}
