package com.piotrkostecki.smarttravelpoznan.presentation.exception;

import android.content.Context;

import com.piotrkostecki.smarttravelpoznan.data.exception.NetworkConnectionException;
import com.piotrkostecki.smarttravelpoznan.data.exception.StopNotFoundException;
import com.piotrkostecki.smarttravelpoznan.presentation.R;

/**
 * Factory used to create error messages from an Exception as a condition.
 */
public class ErrorMessageFactory {

    private ErrorMessageFactory() {
        // empty
    }

    public static String create(Context context, Exception exception) {
        String message = context.getString(R.string.exception_message_generic);

        if (exception instanceof NetworkConnectionException) {
            message = context.getString(R.string.exception_message_no_connection);
        }

        if (exception instanceof StopNotFoundException) {
            message = context.getString(R.string.exception_message_stop_not_found);
        }

        return message;
    }
}
