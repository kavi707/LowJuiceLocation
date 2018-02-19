package com.kavi.droid.lowjuicelocation.services.connections;

import org.json.JSONObject;

/**
 * Created by kavi707 on 1/5/18.
 * @author Kavimal Wijewardana <kavi707@gmail.com>
 */

public abstract class ApiCallResponseHandler implements OnApiCallResponse {

    protected ApiCallResponseHandler() {
    }

    @Override
    public void onNoInternet(Throwable throwable) {
    }

    @Override
    public void onUnAuthorized(JSONObject response, Throwable throwable) {
    }

    @Override
    public void onServiceError(JSONObject response, Throwable throwable) {
    }

    @Override
    public void onNonSuccess(int statusCode, JSONObject response, Throwable throwable) {
    }
}
