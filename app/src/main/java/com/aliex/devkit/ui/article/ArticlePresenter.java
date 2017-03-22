package com.aliex.devkit.ui.article;

import android.media.Image;

import com.aliex.aptlib.ApiFactory;
import com.aliex.devkit.Const;
import com.aliex.devkit.data.LocalFactory;
import com.aliex.devkit.helper.AdapterPresenter;
import com.aliex.devkit.model.ImageInfo;
import com.aliex.devkit.model.Pointer;
import com.apt.annotation.apt.InstanceFactory;
import com.google.gson.Gson;

@InstanceFactory
public class ArticlePresenter extends ArticleContract.Presenter {

    @Override
    public void initAdapterPresenter(AdapterPresenter mAdapterPresenter, ImageInfo mArticle) {
        String article = new Gson().toJson(new Pointer(Image.class.getSimpleName(), mArticle.objectId));
        mAdapterPresenter.setLocalRepository(LocalFactory::getCommentList)
                .setRemoteRepository(ApiFactory::getCommentList).setParams(Const.INCLUDE, Const.CREATER)
                .setParams(Const.ARTICLE, article).setParams(Const.OBJECT_ID, mArticle.objectId).fetch();
    }
}
