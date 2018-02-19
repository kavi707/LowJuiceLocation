package com.kavi.droid.lowjuicelocation.services.connections.exceptions;

import com.kavi.droid.lowjuicelocation.Constants;

/**
 * Created by kavi707 on 1/6/18.
 * @author Kavimal Wijewardana <kavi707@gmail.com>
 */

public class ExNoInternetException extends Exception {

    public ExNoInternetException(Throwable throwable) {
        super(Constants.NO_INTERNET_EXCEPTION_MSG, throwable);
    }


}
