package com.wugang.src.activityresult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.wugang.activityresult.library.ActivityResult;
import com.wugang.activityresult.library.ActivityResultListener;
import com.wugang.activityresult.library.ActivityResultManager;
import com.wugang.activityresult.library.Intercept;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ActivityResultManager.get()
        .registerIntercept(new Intercept() {
          @Override public boolean onIntercept(Activity activity,final ActivityResult activityResult) {
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
      @Override public boolean onIntercept(Activity activity, ActivityResult activityResult) {
        //可以判断是否登录成功，返回对应的值
        Log.e(TAG, "onIntercept2222222222: " );
        return false;
      }
    });
  }

  public void test(View v){
    ActivityResult.of(this)
        .className(TestActivity.class)
        .forResult(new ActivityResultListener() {
          @Override public void onReceiveResult(int resultCode, Intent data) {
            Log.e("------", "onReceiveResult: "+data.getStringExtra("aaa") );
          }
        });
  }
}
