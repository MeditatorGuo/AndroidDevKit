package com.aliex.devkit.data;

import com.aliex.devkit.Const;
import com.aliex.devkit.model.ImageInfo;

import java.util.HashMap;

import io.realm.Realm;
import rx.Observable;

/**
 * author: Aliex <br/>
 * created on: 2017/3/18 <br/>
 * description: <br/>
 */

public class LocalFactory {

    public static Observable getAllImages(HashMap<String, Object> param) {
        return Observable.defer(() -> Realm.getDefaultInstance().where(ImageInfo.class)
                .equalTo(Const.TYPE, (String) param.get(Const.TYPE)).findAll().asObservable());
    }

}
