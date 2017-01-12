package br.com.monitoratec.app.util;

import android.util.Log;

import rx.Subscriber;

/**
 * Created by elder-dell on 2017-01-12.
 */

public abstract class MySubscriber<T> extends Subscriber<T> {

    private static  String TAG = MySubscriber.class.getSimpleName();

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG, e.getMessage());
        onError(e.getMessage());
    }

    protected abstract void onError(String message);

}
