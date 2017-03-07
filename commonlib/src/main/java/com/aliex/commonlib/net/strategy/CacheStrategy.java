package com.aliex.commonlib.net.strategy;

import com.aliex.commonlib.net.core.ApiCache;
import com.aliex.commonlib.net.mode.CacheResult;
import com.aliex.commonlib.utils.JsonUtils;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * author: Aliex <br/>
 * created on: 2017/3/7 <br/>
 * description: <br/>
 */

public abstract class CacheStrategy<T> implements ICacheStrategy {

    Observable<CacheResult<T>> loadCache(ApiCache apiCache, String key, Class<T> clazz) {

        return apiCache.<T> get(key).filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                return s != null;
            }
        }).map(new Func1<String, CacheResult<T>>() {
            @Override
            public CacheResult<T> call(String s) {
                T t = JsonUtils.gson().fromJson(s, clazz);
                return new CacheResult<T>(true, t);
            }
        });
    }

    Observable<CacheResult<T>> loadRemote(final ApiCache apiCache, final String key, Observable<T> source) {
        return source.map(new Func1<T, CacheResult<T>>() {
            @Override
            public CacheResult<T> call(T t) {
                apiCache.put(key, t).subscribeOn(Schedulers.io()).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean status) {
                    }
                });
                return new CacheResult<T>(false, t);
            }
        });
    }

}
