package com.aliex.devkit.data;

import java.util.HashMap;

import io.realm.RealmResults;
import rx.Observable;

/**
 * author: Aliex <br/>
 * created on: 2017/3/18 <br/>
 * description: <br/>
 */

public interface LocalRepository {
    Observable<RealmResults> getData(HashMap<String, Object> param);
}
