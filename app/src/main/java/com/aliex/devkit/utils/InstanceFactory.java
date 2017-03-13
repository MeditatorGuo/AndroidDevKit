package com.aliex.devkit.utils;

/**
 * author: Aliex <br/>
 * created on: 2017/3/13 <br/>
 * description: <br/>
 */

public class InstanceFactory {

    public static Object create(Class mClass) throws IllegalAccessException, InstantiationException {
        switch (mClass.getSimpleName()) {
            case "AdvisePresenter":
                return null;
            case "ArticlePresenter":
                return null;
            case "HomePresenter":
                // return new HomePresenter();
            case "LoginPresenter":
                // return new LoginPresenter();
            case "UserPresenter":
                // return new UserPresenter();
            default:
                return mClass.newInstance();
        }
    }
}
