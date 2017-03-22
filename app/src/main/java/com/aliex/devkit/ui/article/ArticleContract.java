package com.aliex.devkit.ui.article;

import com.aliex.basekit.base.iview.IBaseView;
import com.aliex.basekit.base.presenter.BasePresenter;
import com.aliex.devkit.helper.AdapterPresenter;
import com.aliex.devkit.model.ImageInfo;
import com.aliex.devkit.model.User;

public interface ArticleContract {

    interface View extends IBaseView {
        void commentSuc();

        void commentFail();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void initAdapterPresenter(AdapterPresenter mAdapterPresenter, ImageInfo article);

        @Override
        public void onAttach(View view) {
            super.onAttach(view);
        }
    }
}
