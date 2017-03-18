package com.aliex.devkit.model;

import com.apt.annotation.apt.QueryKey;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * author: Aliex <br/>
 * created on: 2017/3/18 <br/>
 * description: <br/>
 */

public class ImageInfo extends RealmObject implements BaseBean {
    @PrimaryKey
    public String objectId;
    public String image;
    public String article;
    public String author;
    public String title;
    @QueryKey
    public String type;
    public String createdAt;

    @Override
    public String getObjectId() {
        return objectId;
    }
}