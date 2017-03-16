package com.aliex.devkit.model;

import io.realm.RealmObject;

/**
 * author: Aliex <br/>
 * created on: 2017/3/13 <br/>
 * description: <br/>
 */

public class User extends RealmObject implements BaseBean {

    public String objectId;
    public String username;
    public String password;
    public String face;
    public String sessionToken;

    public User() {
    }

    public User(String objectId) {
        this.objectId = objectId;
    }

    public User(String name, String pass) {
        this.username = name;
        this.password = pass;
    }

    @Override
    public String getObjectId() {
        return objectId;
    }
}
