package com.aliex.devkit;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;

import com.aliex.commonlib.utils.CrashHandler;
import com.aliex.devkit.utils.SpUtil;

import java.util.HashMap;
import java.util.Stack;

/**
 * author: Aliex <br/>
 * created on: 2017/3/14 <br/>
 * description: <br/>
 */

public class BaseApplication extends Application {
    private static BaseApplication mApp;
    public Stack<Activity> store;
    public HashMap<String, Object> mCurActivityExtra;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        SpUtil.init(this);
        AppCompatDelegate.setDefaultNightMode(
                SpUtil.isNight() ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        store = new Stack<>();
        registerActivityLifecycleCallbacks(new SwitchBackgroundCallbacks());

        // 崩溃处理
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
        crashHandler.setCrashTip("很抱歉，程序出现异常，即将退出！");
    }

    public static BaseApplication getAppContext() {
        return mApp;
    }

    private class SwitchBackgroundCallbacks implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            store.add(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            store.remove(activity);
        }
    }

    public Activity getCurActivity() {
        return store.lastElement();
    }
}
