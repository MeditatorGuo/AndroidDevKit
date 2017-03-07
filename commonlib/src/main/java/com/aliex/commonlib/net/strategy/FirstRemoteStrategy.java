package com.aliex.commonlib.net.strategy;

import com.aliex.commonlib.net.core.ApiCache;
import com.aliex.commonlib.net.mode.CacheResult;

import rx.Observable;
import rx.functions.Func1;

/**
 * author: Aliex <br/>
 * created on: 2017/3/7 <br/>
 * description: <br/>
 */

public class FirstRemoteStrategy<T> extends CacheStrategy<T> {

    @Override
    public Observable<CacheResult<T>> execute(ApiCache apiCache, String cacheKey, Observable source, Class clazz) {
        Observable<CacheResult<T>> cache = loadCache(apiCache, cacheKey, clazz);
        cache.onErrorReturn(new Func1<Throwable, CacheResult<T>>() {
            @Override
            public CacheResult<T> call(Throwable throwable) {
                return null;
            }
        });

        Observable<CacheResult<T>> remote = loadRemote(apiCache, cacheKey, source);
        return Observable.concat(remote, cache).firstOrDefault(null, new Func1<CacheResult<T>, Boolean>() {
            @Override
            public Boolean call(CacheResult<T> tCacheResult) {
                return tCacheResult != null && tCacheResult.getCacheData() != null;
            }
        });
    }
}
