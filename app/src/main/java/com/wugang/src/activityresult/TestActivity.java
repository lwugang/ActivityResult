package com.wugang.src.activityresult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ((TextView)findViewById(R.id.test)).setText(getClass().getName());
  }

  @Override public void onBackPressed() {
    Intent intent = new Intent();
    intent.putExtra("aaa","-------------TestActivity--------");
    setResult(RESULT_OK,intent);
    super.onBackPressed();
  }
}
