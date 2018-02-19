package com.kavi.droid.lowjuicelocation.services.connections.exceptions;


import com.kavi.droid.lowjuicelocation.Constants;

/**
 * Created by kavi707 on 1/6/18.
 * @author Kavimal Wijewardana <kavi707@gmail.com>
 */

public class ExUnAuthorizedException extends Exception {

    public ExUnAuthorizedException(Throwable throwable) {
        super(Constants.UNAUTHORIZED_EXCEPTION_MSG, throwable);
    }
}
