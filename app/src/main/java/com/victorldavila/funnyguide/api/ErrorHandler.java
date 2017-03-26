package com.victorldavila.funnyguide.api;

import android.util.Log;

import com.victorldavila.funnyguide.models.NetWorkError;

import java.io.IOException;

/**
 * Created by victo on 26/03/2017.
 */

public class ErrorHandler {

    private static final String TAG = ErrorHandler.class.getName();

    public static NetWorkError parseError(Throwable throwable){
        RetrofitException error = (RetrofitException) throwable;
        NetWorkError errorApi = new NetWorkError();
        try {
            errorApi = error.getErrorBodyAs(NetWorkError.class);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());

            errorApi.setStatus_code(0);
            errorApi.setStatus_message(e.getMessage());
        } finally {
            return errorApi;
        }
    }
}
