package com.aliex.devkit.utils;

import com.aliex.devkit.activity.BaseAppCompatActivity;
import com.aliex.devkit.model.User;
import com.google.gson.Gson;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Administrator on 2016/4/5.
 */
public class SpUtil {
    static SharedPreferences prefs;

    public static String getDataByKey(String key) {
        return prefs.getString(key, "");
    }

    public static void init(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean isNight() {
        return prefs.getBoolean("isNight", false);
    }

    public static void setNight(Context context, boolean isNight) {
        prefs.edit().putBoolean("isNight", isNight).commit();
        if (context instanceof BaseAppCompatActivity)
            ((BaseAppCompatActivity) context).reload();
    }

    public static User getUser() {
        return new Gson().fromJson(prefs.getString("user", ""), User.class);
    }

    public static void setUser(User user) {
        prefs.edit().putString("user", new Gson().toJson(user)).commit();
    }
}
