package com.wugang.activityresult.library;

import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author liwugang
 * @title
 * @date 2020-05-18
 * @email 13480020053@163.com
 */
public class BundleCompat {
    private IntentBuilder builder = IntentBuilder.builder(null);

    public static BundleCompat with() {
        return new BundleCompat();
    }

    public BundleCompat put(String key, String value) {
        builder.params(key, value);
        return this;
    }

    public BundleCompat put(String key, boolean value) {
        builder.params(key, value);
        return this;
    }

    public BundleCompat put(String key, int value) {
        builder.params(key, value);
        return this;
    }

    public BundleCompat put(String key, Serializable value) {
        builder.params(key, value);
        return this;
    }

    public BundleCompat put(String key, Parcelable value) {
        builder.params(key, value);
        return this;
    }

    public Bundle create() {
        return builder.build().getExtras();
    }
}
