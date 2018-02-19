package com.kavi.droid.lowjuicelocation.services.connections.exceptions;

import com.kavi.droid.lowjuicelocation.Constants;

/**
 * Created by kavi707 on 1/6/18.
 * @author Kavimal Wijewardana <kavi707@gmail.com>
 */

public class ExNonSuccessException extends Exception {

    public ExNonSuccessException(Throwable throwable) {
        super(Constants.NON_SUCCESS_EXCEPTION_MSG, throwable);
    }
}
