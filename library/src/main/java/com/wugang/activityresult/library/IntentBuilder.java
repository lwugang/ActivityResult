package com.wugang.activityresult.library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import java.io.Serializable;

/**
 * Created by lwg on 2016/7/20.
 * intent 构建工具类
 */
public class IntentBuilder {
  private Context context;
  private Intent intent;

  private IntentBuilder(Context context) {
    this.context = context;
    intent = new Intent();
  }

  public static final IntentBuilder builder(Context context) {
    return new IntentBuilder(context);
  }

  /**
   * 添加启动标记
   */
  public IntentBuilder flag(int flags) {
    intent.setFlags(flags);
    return this;
  }

  /**
   * 动作
   */
  public IntentBuilder action(String action) {
    intent.setAction(action);
    return this;
  }

  public IntentBuilder params(Bundle bundle) {
    intent.putExtras(bundle);
    return this;
  }

  /**
   * 参数添加
   */
  public IntentBuilder params(String key, String value) {
    intent.putExtra(key, value);
    return this;
  }

  /**
   * 参数添加
   */
  public IntentBuilder params(String key, int value) {
    intent.putExtra(key, value);
    return this;
  }

  /**
   * 参数添加
   */
  public IntentBuilder params(String key, boolean value) {
    intent.putExtra(key, value);
    return this;
  }

  /**
   * 参数添加
   */
  public IntentBuilder params(String key, Serializable value) {
    intent.putExtra(key, value);
    return this;
  }

  /**
   * 参数添加
   */
  public IntentBuilder params(String key, Parcelable value) {
    intent.putExtra(key, value);
    return this;
  }

  /**
   * 设置类名
   */
  public IntentBuilder className(Class clazz) {
    intent.setClass(context, clazz);
    return this;
  }
  /**
   * 设置类名
   */
  public IntentBuilder className(String className) {
    try {
      className(Class.forName(className));
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return this;
  }

  /**
   * 构建Intent
   */
  public Intent build() {
    return intent;
  }

  /**
   * 启动Activity
   */
  public void start() {
    context.startActivity(build());
  }

  /**
   * 启动Activity带结果返回的
   */
  public void start(int requestCode) {
    ((Activity) context).startActivityForResult(build(), requestCode);
  }
}