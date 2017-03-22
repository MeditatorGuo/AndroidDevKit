package com.aliex.devkit.helper;

import android.view.View;

import com.aliex.aptlib.TRouter;
import com.apt.annotation.aspect.SingleClick;

import java.util.HashMap;

/**
 * author: Aliex <br/>
 * created on: 2017/3/22 <br/>
 * description: <br/>
 */

public class RouterHelper {

    @SingleClick // 防止连续点击
    public static void go(String actionName, HashMap data, View view) {
        TRouter.go(actionName, data, view);
    }

}
