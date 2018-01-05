package com.wugang.activityresult.library;

import android.app.Activity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author lwg
 * @e-mail 13480020053@163.com
 * @time 1/4/18
 * @desc 结果回调管理
 */

public final class ActivityResultManager {
  private static ActivityResultManager INSTANCE = new ActivityResultManager();

  private CopyOnWriteArrayList<InterceptEntity> interceptList = new CopyOnWriteArrayList<>();

  public static ActivityResultManager get() {
    return INSTANCE;
  }

  /**
   * 注册拦截器,可以注册多个
   */
  public ActivityResultManager registerIntercept(Intercept intercept) {
    registerIntercept(intercept, false);
    return INSTANCE;
  }

  /**
   * 注册拦截器,可以注册多个
   *
   * @param removeUsed 使用后就自动移除
   */
  public ActivityResultManager registerIntercept(Intercept intercept, boolean removeUsed) {
    InterceptEntity interceptEntity = InterceptEntity.of(intercept, removeUsed);
    if (!interceptList.contains(interceptEntity)) {
      interceptList.add(interceptEntity);
    }
    return INSTANCE;
  }

  /**
   * 取消注册
   */
  public ActivityResultManager unRegisterIntercept(Intercept intercept) {
    interceptList.remove(intercept);
    return INSTANCE;
  }

  protected void intercept(Activity activity, ActivityResult activityResult, Class[] intercepts) {
    List<String> list =
        intercepts == null ? Collections.EMPTY_LIST : getInterceptsClassName(intercepts);
    //取出上一次执行记录的拦截器位置
    int i =
        Math.max(interceptList.indexOf(InterceptEntity.of(activityResult.currentIntercept, false)),
            0);
    for (; i < interceptList.size(); i++) {
      InterceptEntity entity = interceptList.get(i);
      if (list.isEmpty() || list.contains(entity.intercept.getClass().getName())) {
        boolean isIntercept = entity.intercept.onIntercept(activity, activityResult);
        if (entity.removeUsed) {
          //使用后移除
          interceptList.remove(entity);
        }
        if (isIntercept) {
          if (interceptList.size() > (i + 1)) {
            //记录下一个需要执行的拦截器
            activityResult.currentIntercept = interceptList.get(i + 1).intercept;
          }
          return;
        }
      }
    }
    //执行所有拦截器之后启动对应界面
    activityResult.startActivity();
  }

  private List<String> getInterceptsClassName(Class[] intercepts) {
    List<String> list = new ArrayList<>();
    for (int i = 0; i < intercepts.length; i++) {
      list.add(intercepts[i].getName());
    }
    return list;
  }

  static class InterceptEntity {
    Intercept intercept;
    /**
     * 使用之后自动移除拦截器
     */
    boolean removeUsed;

    public InterceptEntity(Intercept intercept, boolean removeUsed) {
      this.intercept = intercept;
      this.removeUsed = removeUsed;
    }

    public static InterceptEntity of(Intercept intercept, boolean removeUsed) {
      return new InterceptEntity(intercept, removeUsed);
    }

    @Override public boolean equals(Object obj) {
      InterceptEntity entity = (InterceptEntity) obj;
      return intercept == entity.intercept;
    }
  }
}
