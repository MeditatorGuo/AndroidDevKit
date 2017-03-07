package com.aliex.commonlib.net.strategy;

import com.aliex.commonlib.net.core.ApiCache;
import com.aliex.commonlib.net.mode.CacheResult;

import rx.Observable;

/**
 * @Description: 缓存策略--只取网络
 * @author: jeasinlee
 * @date: 16/12/31 14:30.
 */
public class OnlyRemoteStrategy<T> extends CacheStrategy<T> {

    @Override
    public Observable<CacheResult<T>> execute(ApiCache apiCache, String cacheKey, Observable source, Class clazz) {
        return loadRemote(apiCache, cacheKey, source);
    }
}
