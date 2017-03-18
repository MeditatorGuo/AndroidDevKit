package com.aliex.devkit.ui.home;

import com.aliex.basekit.base.iview.IBaseView;
import com.aliex.basekit.base.presenter.BasePresenter;
import com.aliex.devkit.model.User;

/**
 * author: Aliex <br/>
 * created on: 2017/3/13 <br/>
 * description: <br/>
 */

public interface HomeContract {
    interface View extends IBaseView {
        void showTabList(String[] mTabs);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getTabList();

    }

}
