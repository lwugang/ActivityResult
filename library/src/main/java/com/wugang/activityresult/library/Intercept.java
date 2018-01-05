package com.wugang.activityresult.library;

import android.app.Activity;

/**
 * @author lwg
 * @e-mail 13480020053@163.com
 * @time 1/4/18
 * @desc 拦截器,通过ActivityResult启动activity之前会触发拦截器
 */
public interface Intercept {

  /**
   * 拦截处理
   * @param activity
   * @param activityResult
   * @return 是否拦截  true 表示拦截不继续执行
   */
  boolean onIntercept(Activity activity, ActivityResult activityResult);
}
