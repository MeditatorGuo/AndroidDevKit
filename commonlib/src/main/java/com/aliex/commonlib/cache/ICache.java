package com.aliex.commonlib.cache;

/**
 * author: Aliex <br/>
 * created on: 2017/3/7 <br/>
 * description: <br/>
 */

public interface ICache {
    void put(String key, Object value);

    Object get(String key);

    boolean contains(String key);

    void remove(String key);

    void clear();

}
