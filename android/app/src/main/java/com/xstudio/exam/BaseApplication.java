package com.xstudio.exam;

import android.app.Application;

import com.jude.beam.Beam;
import com.jude.utils.JUtils;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化
        JUtils.initialize(this);
        JUtils.setDebug(BuildConfig.DEBUG, "DrivingExam");
        Beam.init(this);
    }
}
