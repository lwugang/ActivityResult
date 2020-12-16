package com.wugang.activityresult.library;

import android.content.Intent;

/**
 * @author lwg
 * @e-mail 13480020053@163.com
 * @time 1/4/18
 * @desc Activity Result 结果回调
 */

public interface AResultListener {
    /**
     * 接收结果处理
     *
     * @param data 被打开Activity返回的结果数据
     */
    void onReceiveResult(Intent data);
}
