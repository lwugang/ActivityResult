package com.wugang.src.activityresult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.wugang.activityresult.library.AResult;
import com.wugang.activityresult.library.IntentBuilder;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.test)).setText(getClass().getName());
    }

    @Override
    public void onBackPressed() {
        AResult.setResultAndFinish(this, IntentBuilder.of("aaa", "-----TestActivity-----"));
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
