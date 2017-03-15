package com.aliex.devkit.event;

import android.os.Message;

/**
 * author: Aliex <br/>
 * created on: 2017/3/14 <br/>
 * description: <br/>
 */

public interface Event {
    void call(Message msg);
}
