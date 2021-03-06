package com.aliex.devkit.ui.home;

import com.aliex.devkit.event.EventBus;
import com.aliex.devkit.event.EventTags;
import com.aliex.devkit.model.User;
import com.aliex.devkit.utils.SpUtil;
import com.apt.annotation.apt.InstanceFactory;
import com.apt.annotation.javassist.Bus;
import com.apt.annotation.javassist.BusRegister;
import com.apt.annotation.javassist.BusUnRegister;

/**
 * author: Aliex <br/>
 * created on: 2017/3/13 <br/>
 * description: <br/>
 */
@InstanceFactory
public class HomePresenter extends HomeContract.Presenter {

    @Override
    public void onAttach(HomeContract.View view) {
        super.onAttach(view);
        initEvent();
        getTabList();
    }

    @Override
    public void getTabList() {
        String[] mTabs = { "民谣", "摇滚", "流行", "故事" };
        EventBus.getInstance().onEvent(EventTags.SHOW_TAB_LIST, mTabs);
    }

    @Bus(EventTags.SHOW_TAB_LIST)
    public void showTabList(String[] tabs) {
        getView().showTabList(tabs);
    }

    @BusRegister
    private void initEvent() {
    }

    @Override
    @BusUnRegister
    public void onDetach() {
        super.onDetach();
    }

}
