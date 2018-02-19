package com.kavi.droid.lowjuicelocation.services.connections;

import com.kavi.droid.lowjuicelocation.models.LocationDetail;

import org.json.JSONObject;

/**
 * Created by kavi707 on 12/26/17.
 * @author Kavimal Wijewardana <kavi707@gmail.com>
 */

public interface OnApiCallResponse {
    void onSuccess(int statusCode, LocationDetail locationDetail);
    void onNoInternet(Throwable throwable);
    void onUnAuthorized(JSONObject response, Throwable throwable);
    void onNonSuccess(int statusCode, JSONObject response, Throwable throwable);
    void onServiceError(JSONObject response, Throwable throwable);
}
