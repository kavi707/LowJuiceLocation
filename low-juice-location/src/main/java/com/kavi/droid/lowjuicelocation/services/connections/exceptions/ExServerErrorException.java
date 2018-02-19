package com.kavi.droid.lowjuicelocation.services.connections.exceptions;

import com.kavi.droid.lowjuicelocator.Constants;

/**
 * Created by kavi707 on 1/6/18.
 * @author Kavimal Wijewardana <kavi707@gmail.com>
 */

public class ExServerErrorException extends Exception {

    public ExServerErrorException(Throwable throwable) {
        super(Constants.SERVER_ERROR_EXCEPTION_MSG, throwable);
    }
}
