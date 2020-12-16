package com.wugang.activityresult.library;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;

/**
 * @author lwg
 * @e-mail 13480020053@163.com
 * @time 1/4/18
 * @desc activity 结果代理fragment
 */

public class AResultFragment extends Fragment {

    private AResultListener activityResultListener;

    public AResultFragment() {
    }

    public void setActivityResultListener(AResultListener activityResultListener) {
        this.activityResultListener = activityResultListener;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (activityResultListener != null && resultCode == Activity.RESULT_OK) {
            activityResultListener.onReceiveResult(data);
        }
    }
}
