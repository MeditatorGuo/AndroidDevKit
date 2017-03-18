package com.aliex.devkit.model;

import java.util.ArrayList;

import io.realm.RealmObject;

public class DataArr<T extends RealmObject> {
    public ArrayList<T> results;
}
