package com.aliex.devkit.ui.user;

import com.aliex.basekit.base.iview.IBaseView;
import com.aliex.basekit.base.presenter.BasePresenter;
import com.aliex.devkit.model.User;

import java.io.File;

/**
 * author: Aliex <br/>
 * created on: 2017/3/13 <br/>
 * description: <br/>
 */

public interface UserContract {
    interface View extends IBaseView {
        void showMsg(String msg);

        void initUser(User user);

        void takePhoto();
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void upLoadFace(File f);

        public abstract void upUserInfo(String face);

    }
}
