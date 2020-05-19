package com.wugang.activityresult.library;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * @author lwg
 * @e-mail 13480020053@163.com
 * @time 1/4/18
 * @desc Activity跳转结果回调处理
 */
public final class AResult {
    static final String TAG_ACTIVITY_RESULT_FRAGMENT = "ActivityResult";

    AResultFragment resultFragment;

    private IntentBuilder intentBuilder;

    /**
     * 绿色通道
     */
    boolean greenChannel = false;

    /**
     * 当前拦截停止的的拦截器,继续执行时从此拦截器执行
     */
    Intercept currentIntercept;

    /**
     * 需要执行的拦截器
     */
    Class[] intercepts;

    /**
     * Transition 动画
     */
    Bundle bundle;

    public AResult(Activity activity) {
        intentBuilder = IntentBuilder.builder(activity);
        resultFragment = getActivityResultFragment(activity);
    }

    public static AResult of(@NonNull Activity activity) {
        return new AResult(activity);
    }

    public static AResult of(@NonNull Fragment fragment) {
        return new AResult(fragment.getActivity());
    }

    public Intercept getCurrentIntercept() {
        return currentIntercept;
    }

    /**
     * 拦截器，一次性的，使用后会自动移除
     */
    public AResult intercept(Intercept intercept) {
        AResultManager.get()
                .registerIntercept(intercept, true);
        return this;
    }

    /**
     * transition动画 {@link ActivityOptions#toBundle()}
     */
    public AResult options(Bundle options) {
        bundle = options;
        return this;
    }

    /**
     * 绿色通道，不走任何拦截器,在拦截器中使用ActivityResult跳转需要使用绿色通道，否则会造成死循环
     */
    public AResult greenChannel() {
        greenChannel = true;
        return this;
    }

    /**
     * 绿色通道，只执行指定拦截器,在拦截器中使用ActivityResult跳转需要使用绿色通道，否则会造成死循环
     */
    public AResult greenChannel(Class<Intercept>... intercepts) {
        greenChannel = true;
        this.intercepts = intercepts;
        return this;
    }

    /**
     * 添加启动标记
     */
    public AResult flag(int flags) {
        intentBuilder.flag(flags);
        return this;
    }

    /**
     * 动作
     */
    public AResult action(String action) {
        intentBuilder.action(action);
        return this;
    }

    public AResult params(Bundle bundle) {
        intentBuilder.params(bundle);
        return this;
    }

    /**
     * 参数添加
     */
    public AResult params(String key, String value) {
        intentBuilder.params(key, value);
        return this;
    }

    /**
     * 参数添加
     */
    public AResult params(String key, int value) {
        intentBuilder.params(key, value);
        return this;
    }

    /**
     * 参数添加
     */
    public AResult params(String key, boolean value) {
        intentBuilder.params(key, value);
        return this;
    }

    /**
     * 参数添加
     */
    public AResult params(String key, Serializable value) {
        intentBuilder.params(key, value);
        return this;
    }

    /**
     * 参数添加
     */
    public AResult params(String key, Parcelable value) {
        intentBuilder.params(key, value);
        return this;
    }

    /**
     * 设置类名
     */
    public AResult className(Class clazz) {
        intentBuilder.className(clazz);
        return this;
    }

    /**
     * 设置类名
     */
    public AResult className(String className) {
        intentBuilder.className(className);
        return this;
    }

    /**
     * 构建Intent
     */
    public Intent build() {
        return intentBuilder.build();
    }

    /**
     * 以返回值方式打开
     *
     * @param activityResultListener 可以为 null,null表示不需要处理返回值
     */
    public void forResult(@Nullable AResultListener activityResultListener) {
        //绿色通道不走拦截器
        resultFragment.setActivityResultListener(activityResultListener);
        if (!greenChannel) {
            execIntercepts();
        } else {
            startActivity();
        }
    }

    /**
     * 继续跳转，恢复跳转逻辑
     */
    public void onContinue() {
        execIntercepts();
    }

    private void execIntercepts() {
        AResultManager.get().intercept(resultFragment.getActivity(), this, intercepts);
    }

    protected void startActivity() {
        if (Build.VERSION.SDK_INT >= 16) {
            resultFragment.startActivityForResult(build(), 1, bundle);
        } else {
            resultFragment.startActivityForResult(build(), 1);
        }
    }

    /**
     * 查找ActivityResultFragment
     */
    Fragment findActivityResultFragment(Activity activity) {
        return activity.getFragmentManager().findFragmentByTag(TAG_ACTIVITY_RESULT_FRAGMENT);
    }

    private AResultFragment getActivityResultFragment(Activity activity) {
        Fragment activityResultFragment = findActivityResultFragment(activity);
        if (activityResultFragment == null) {
            FragmentManager fragmentManager = activity.getFragmentManager();
            activityResultFragment = new AResultFragment();
            fragmentManager.beginTransaction()
                    .add(activityResultFragment, TAG_ACTIVITY_RESULT_FRAGMENT)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return (AResultFragment) activityResultFragment;
    }
}
