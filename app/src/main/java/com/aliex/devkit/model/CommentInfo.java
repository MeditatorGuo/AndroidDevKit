package com.aliex.devkit.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CommentInfo extends RealmObject implements BaseBean {
    @PrimaryKey
    public String objectId;
    public ImageInfo article;
    public String content;
    public User creater;

    @Override
    public String getObjectId() {
        return objectId;
    }
}
