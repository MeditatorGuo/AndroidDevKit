package com.aliex.devkit.data;

import com.aliex.devkit.model.DataArr;

import java.util.HashMap;

import rx.Observable;

/**
 * author: Aliex <br/>
 * created on: 2017/3/18 <br/>
 * description: <br/>
 */

public interface RemoteRepository {
    Observable<DataArr> getData(HashMap<String, Object> param);
}
