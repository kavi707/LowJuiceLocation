package com.kavi.droid.lowjuicelocation.models;

/**
 * Created by kavi707 on 2/11/18.
 * @author Kavimal Wijewardana <kavi707@gmail.com>
 */

public class NetworkOperatorInfo {

    public static final String GSM_TYPE = "gsm";
    public static final String CDMA_TYPE = "cdma";
    public static final String UMTS_TYPE = "umts";
    public static final String LTE_TYPE = "lte";
    public static final String UNKNOWN_TYPE = "unknown";

    /**
     * MCC - Mobile Country Code
     * This is the country code. It always has 3 digits. Some countries can use more than one MCC.
     */
    private int mcc;

    /**
     * MNC - Mobile Network Code
     * This is the network code. It can have 2 or 3 digits.
     */
    private int mnc;

    /**
     * Operator current type
     * LTE, UMTS, CDMA, GSM
     */
    private String networkType;

    public NetworkOperatorInfo(int mcc, int mnc, String networkType) {
        this.mcc = mcc;
        this.mnc = mnc;
        this.networkType = networkType;
    }

    public int getMcc() {
        return mcc;
    }

    public int getMnc() {
        return mnc;
    }

    public String getNetworkType() {
        return networkType;
    }
}
