package com.kavi.droid.lowjuicelocation.exceptions;

import com.kavi.droid.lowjuicelocation.Constants;

/**
 * Created by kavi707 on 2/20/18.
 * @author Kavimal Wijewardana <kavi707@gmail.com>
 */

public class AirplaneModeException extends Exception {

    public AirplaneModeException() {
        super(Constants.AIRPLANE_MODE_EXCEPTION_MSG);
    }
}
