package com.aliex.devkit.event;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.SparseArray;

import com.apt.annotation.javassist.Bus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * author: Aliex <br/>
 * created on: 2017/3/14 <br/>
 * description: <br/>
 */

public class EventBus {

    private ConcurrentHashMap<Integer, List<SparseArray<Event>>> mEventList = new ConcurrentHashMap<>();// 存储所有事件ID以及其回调
    private ConcurrentHashMap<Integer, Object> mStickyEventList = new ConcurrentHashMap<>();// 存储粘连事件ID以及其数据
    private ScheduledExecutorService mPool = Executors.newScheduledThreadPool(5);
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private EventBus() {
    }

    private static class Holder {
        public static EventBus INSTANCE = new EventBus();
    }

    public static EventBus getInstance() {
        return Holder.INSTANCE;
    }

    public EventBus register(int tag, Event ev) {
        register(tag, ev, Bus.DEFAULT);
        return this;
    }

    public EventBus register(int tag, final Event ev, int thread) {
        SparseArray<Event> mEvent = new SparseArray<>();
        mEvent.put(thread, ev);
        if (mEventList.get(tag) != null) {
            mEventList.get(tag).add(mEvent);
        } else {
            List<SparseArray<Event>> mList = new ArrayList<>();
            mList.add(mEvent);
            mEventList.put(tag, mList);
        }
        if (mStickyEventList.get(tag) != null) {// 注册时分发粘连事件
            final Message msg = new Message();
            msg.obj = mStickyEventList.get(tag);
            msg.what = tag;
            callEvent(msg, ev, thread);
        }
        return this;
    }

    private void callEvent(final Message msg, final Event ev, int thread) {
        switch (thread) {
            case Bus.DEFAULT:
                ev.call(msg);
                break;
            case Bus.UI:
                mHandler.post(() -> ev.call(msg));
                break;
            case Bus.BG:
                mPool.execute(() -> ev.call(msg));
                break;
        }
    }

    public EventBus unRegister(int tag) {
        if (mEventList.get(tag) != null)
            mEventList.remove(tag);
        return this;
    }

    public EventBus onEvent(int tag, Object data) {
        Message msg = new Message();
        msg.obj = data;
        msg.what = tag;
        if (mEventList.get(tag) != null) {
            for (SparseArray<Event> ev : mEventList.get(tag))
                callEvent(msg, ev.valueAt(0), ev.keyAt(0));
        }
        return this;
    }

    public EventBus onEvent(int tag) {
        onEvent(tag, null);
        return this;
    }

    public EventBus onStickyEvent(int tag, Object data) {
        mStickyEventList.put(tag, (data == null ? tag : data));
        onEvent(tag, data);
        return this;
    }

    public EventBus onStickyEvent(int tag) {
        onStickyEvent(tag, null);
        return this;
    }
}
