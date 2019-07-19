package com.myxh.tms.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.myxh.tms.R;
import com.myxh.tms.common.LocationService;
import com.myxh.tms.ui.base.BaseActivity;
import com.myxh.tms.util.SharePreferenceUtil;

/**
 * Created by asus on 2016/8/28.
 */
public class SplashActivity extends BaseActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //开启定位服务
        startService(new Intent(this, LocationService.class));

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isFirst = SharePreferenceUtil.getBoolean(SplashActivity.this,"isFirst",true);
                if (isFirst) {
                    /*SharePreferenceUtil.putBoolean(SplashActivity.this,"isFirst",false);
                    openActivity(GuideActivity.class);*/
                    openActivity(LoginActivity.class);
                } else {
                    //openActivity(MainActivity.class);
                    openActivity(LoginActivity.class);
                }
                finish();
            }
        },2000);
    }

}
