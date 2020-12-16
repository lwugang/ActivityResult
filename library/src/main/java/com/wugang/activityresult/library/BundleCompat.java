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
    private IntentBuilder builder = IntentBuilder.of();

    private BundleCompat(Bundle bundle) {
        builder.params(bundle);
    }

    private BundleCompat() {
    }

    public static BundleCompat of(Bundle bundle) {
        return new BundleCompat(bundle);
    }

    public static BundleCompat of(String key, String value) {
        BundleCompat bundleCompat = new BundleCompat();
        bundleCompat.put(key, value);
        return bundleCompat;
    }

    public static BundleCompat of(String key, int value) {
        BundleCompat bundleCompat = new BundleCompat();
        bundleCompat.put(key, value);
        return bundleCompat;
    }

    public static BundleCompat of(String key, boolean value) {
        BundleCompat bundleCompat = new BundleCompat();
        bundleCompat.put(key, value);
        return bundleCompat;
    }

    public static BundleCompat of() {
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
