package com.wugang.src.activityresult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.wugang.activityresult.library.AResult;
import com.wugang.activityresult.library.AResultListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void test(View v) {
        AResult.of(this).className(TestActivity.class)
                .options(ActivityOptionsCompat.makeScaleUpAnimation(v, (int) v.getX(), (int) v.getY(),
                        v.getWidth() / 2, v.getHeight() / 2).toBundle()).forResult(new AResultListener() {
            @Override
            public void onReceiveResult(Intent data) {
                Log.e(TAG, "onReceiveResult: "+data.getStringExtra("aaa") );
            }
        });
    }
}
