package com.aliex.devkit.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MessageInfo extends RealmObject implements BaseBean {
    @PrimaryKey
    public String objectId;
    public User receiver;
    public String message;
    public String uId;
    public User creater;

    @Override
    public String getObjectId() {
        return objectId;
    }
}
