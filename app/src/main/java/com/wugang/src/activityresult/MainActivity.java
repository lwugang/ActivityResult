package com.wugang.src.activityresult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.wugang.activityresult.library.AResult;
import com.wugang.activityresult.library.AResultListener;
import com.wugang.activityresult.library.AResultManager;
import com.wugang.activityresult.library.BundleCompat;
import com.wugang.activityresult.library.Intercept;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    AResultManager.get()
        .registerIntercept(new Intercept() {
          @Override public boolean onIntercept(Activity activity,final AResult activityResult) {
            Log.e(TAG, "onIntercept: " );
            new Thread(){
              @Override public void run() {
                super.run();
                SystemClock.sleep(2000);
                activityResult.onContinue();
              }
            }.start();
            return true;
          }
        },true).registerIntercept(new Intercept() {
      @Override public boolean onIntercept(Activity activity, AResult activityResult) {
        //可以判断是否登录成功，返回对应的值
        Log.e(TAG, "onIntercept2222222222: " );
        return false;
      }
    },true);
  }

  public void test(View v){
    AResult.of(this)
        .className(TestActivity.class)
        .greenChannel()
        .options(ActivityOptionsCompat.makeScaleUpAnimation(v,(int)v.getX(),(int)v.getY(),
            v.getWidth()/2,v.getHeight()/2).toBundle())
        .intercept(new Intercept() {
          @Override public boolean onIntercept(Activity activity, AResult activityResult) {
            Log.e(TAG, "onIntercept: test" );
            return false;
          }
        }).forResult(new AResultListener() {
      @Override public void onReceiveResult(int resultCode, Intent data) {

      }
    });
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    AResultManager.get().clearIntercept();
  }
}
