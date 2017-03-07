package com.aliex.basekit;

import android.app.Application;

import com.aliex.commonlib.utils.CrashHandler;

/**
 * author: Aliex <br/>
 * created on: 2017/2/27 <br/>
 * description: <br/>
 */

public class BaseKitApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 崩溃处理
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
        crashHandler.setCrashTip("很抱歉，程序出现异常，即将退出！");
    }
}
