package com.aliex.commonlib.net.core;

import android.content.Context;

import com.aliex.commonlib.net.mode.CookiesStore;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * author: Aliex <br/>
 * created on: 2017/3/7 <br/>
 * description: <br/>
 */

public class ApiCookie implements CookieJar {

    private Context mContext;
    private CookiesStore cookiesStore;


    public ApiCookie(Context context) {
        this.mContext = context;
        if (cookiesStore == null) {
            cookiesStore = new CookiesStore(mContext);
        }
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookiesStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookiesStore.get(url);
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }
}
