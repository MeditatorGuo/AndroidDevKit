package com.aliex.devkit.activity;

import java.lang.reflect.ParameterizedType;

import com.aliex.basekit.base.iview.IBaseView;
import com.aliex.basekit.base.presenter.BasePresenter;
import com.aliex.commonlib.utils.ActivityManagerUtils;
import com.aliex.commonlib.utils.DrawerToast;
import com.aliex.devkit.utils.InstanceUtil;

import android.app.Activity;
import android.databinding.ViewDataBinding;

/**
 * author: Aliex <br/>
 * created on: 2017/2/27 <br/>
 * description: <br/>
 */

public abstract class BaseAppCompatActivity<P extends BasePresenter, B extends ViewDataBinding>
        extends DataBindingActivity<B> {
    public P mPresenter;

    @Override
    protected void initPresenter() {
        if (this instanceof IBaseView && this.getClass().getGenericSuperclass() instanceof ParameterizedType
                && ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments().length > 0) {
            Class mPresenterClass = (Class) ((ParameterizedType) (this.getClass().getGenericSuperclass()))
                    .getActualTypeArguments()[0];
            mPresenter = InstanceUtil.getInstance(mPresenterClass);
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

}
