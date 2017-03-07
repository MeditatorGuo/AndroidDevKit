package com.aliex.commonlib.net.strategy;

import com.aliex.commonlib.net.core.ApiCache;
import com.aliex.commonlib.net.mode.CacheResult;

import rx.Observable;

/**
 * author: Aliex <br/>
 * created on: 2017/3/7 <br/>
 * description: <br/>
 */

public interface ICacheStrategy<T> {

    Observable<CacheResult<T>> execute(ApiCache apiCache, String cacheKey, Observable<T> source, Class<T> clazz);

}
