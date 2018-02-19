package com.kavi.droid.lowjuicelocation.services.connections;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.kavi.droid.lowjuicelocation.Constants;
import com.kavi.droid.lowjuicelocation.models.NetworkCellInfo;
import com.kavi.droid.lowjuicelocation.models.NetworkOperatorInfo;
import com.kavi.droid.lowjuicelocation.services.connections.exceptions.ExNoInternetException;
import com.kavi.droid.lowjuicelocation.services.connections.exceptions.ExNonSuccessException;
import com.kavi.droid.lowjuicelocation.services.connections.exceptions.ExServerErrorException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.conn.ConnectTimeoutException;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by kavi707 on 10/3/17.
 * @author Kavimal Wijewardana <kavi707@gmail.com>
 */

public class ApiCalls {

    public final String TAG = this.getClass().getName();

    public static final String HEADER_AUTHORIZATION = "Authorization";

    private static final String APPLICATION_JSON = "application/json";

    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private SyncHttpClient syncHttpClient = new SyncHttpClient();

    /**
     * Create JsonHttpResponseHandler object
     * @param apiCallResponseHandler ApiCallResponseHandler object
     * @return JsonHttpResponseHandler
     */
    private JsonHttpResponseHandler getJsonHttpResponseHandler(final ApiCallResponseHandler apiCallResponseHandler) {
        JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(TAG, "getJsonHttpResponseHandler: onSuccess: responseJsonString -> " + response.toString());
                apiCallResponseHandler.onSuccess(statusCode, Utils.getInstance().getLocationDetails(response));
            }

            @Override
            public void onFailure(final int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                if (errorResponse != null) {
                    Log.d(TAG, "getJsonHttpResponseHandler: onFailure: errorResponseJsonString -> " + errorResponse.toString());
                }

                Throwable connectionThrowable;

                if (throwable instanceof ConnectTimeoutException ||
                        throwable instanceof NetworkErrorException ||
                        throwable instanceof ConnectException ||
                        throwable instanceof SocketTimeoutException) {
                    connectionThrowable = new ExNoInternetException(throwable);
                    apiCallResponseHandler.onNoInternet(connectionThrowable);
                } else {
                    if (statusCode == 500) {
                        connectionThrowable = new ExServerErrorException(throwable);
                        apiCallResponseHandler.onServiceError(errorResponse, connectionThrowable);
                    } if (statusCode == 401) {
                        connectionThrowable = new ExServerErrorException(throwable);
                        apiCallResponseHandler.onUnAuthorized(errorResponse, connectionThrowable);
                    } else {
                        connectionThrowable = new ExNonSuccessException(throwable);
                    }
                }

                apiCallResponseHandler.onNonSuccess(statusCode, errorResponse, connectionThrowable);
            }
        };

        return responseHandler;
    }

    public void getLocation(Context context,
                            @NonNull String taskMethod,
                            @NonNull NetworkOperatorInfo networkOperatorInfo,
                            @NonNull NetworkCellInfo networkCellInfo,
                            ApiCallResponseHandler apiCallResponseHandler) {

        String url = Constants.LOCATION_SERVICE_API;
        Log.d(TAG, "getLocation: POST: url -> " + url);

        JSONObject reqObj = new JSONObject();
        JSONObject cellObj = new JSONObject();
        JSONArray cellsJsonArray = new JSONArray();

        try {

            reqObj.put("token", "9b4df8b4d53e86");
            reqObj.put("radio", networkOperatorInfo.getNetworkType());
            reqObj.put("mcc", networkOperatorInfo.getMcc());
            reqObj.put("mnc", networkOperatorInfo.getMnc());
            reqObj.put("address", 1);

            cellObj.put("lac", networkCellInfo.getLac());
            cellObj.put("cid", networkCellInfo.getCellId());
            cellObj.put("psc", 1);

            cellsJsonArray.put(cellObj);

            reqObj.put("cells", cellsJsonArray);

            String reqJsonString = reqObj.toString();
            Log.d(TAG, "getLocation: reqJsonString -> " + reqJsonString);

            JsonHttpResponseHandler responseHandler = getJsonHttpResponseHandler(apiCallResponseHandler);

            if (taskMethod.equals(Constants.SYNC_METHOD)) {
                syncHttpClient.post(context, url, new StringEntity(reqJsonString), APPLICATION_JSON, responseHandler);
            } else if (taskMethod.equals(Constants.ASYNC_METHOD)) {
                asyncHttpClient.post(context, url, new StringEntity(reqJsonString), APPLICATION_JSON, responseHandler);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
