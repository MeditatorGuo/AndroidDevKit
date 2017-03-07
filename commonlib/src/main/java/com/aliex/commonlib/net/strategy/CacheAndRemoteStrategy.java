package com.aliex.commonlib.net.strategy;

import com.aliex.commonlib.net.core.ApiCache;
import com.aliex.commonlib.net.mode.CacheResult;

import rx.Observable;
import rx.functions.Func1;

/**
 * @Description: 缓存策略--缓存和网络
 * @author: jeasinlee
 * @date: 16/12/31 14:33.
 */
public class CacheAndRemoteStrategy<T> extends CacheStrategy<T> {

    @Override
    public Observable<CacheResult<T>> execute(ApiCache apiCache, String cacheKey, Observable source, Class clazz) {
        Observable<CacheResult<T>> cache = loadCache(apiCache, cacheKey, clazz);
        final Observable<CacheResult<T>> remote = loadRemote(apiCache, cacheKey, source);

        return Observable.concat(cache, remote).filter(new Func1<CacheResult<T>, Boolean>() {
            @Override
            public Boolean call(CacheResult<T> tCacheResult) {
                return tCacheResult.getCacheData() != null;
            }
        });
    }
}
