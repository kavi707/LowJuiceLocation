package com.kavi.droid.lowjuicelocation.models;

/**
 * Created by kavi707 on 2/11/18.
 * @author Kavimal Wijewardana <kavi707@gmail.com>
 */

public class NetworkCellInfo {

    private int cellId;
    private int lac;

    public NetworkCellInfo(int cellId, int lac) {
        this.cellId = cellId;
        this.lac = lac;
    }

    public int getCellId() {
        return cellId;
    }

    public int getLac() {
        return lac;
    }
}
