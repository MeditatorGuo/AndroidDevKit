package com.aliex.devkit.ui.article;

import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.aliex.devkit.Const;
import com.aliex.devkit.R;
import com.aliex.devkit.activity.BaseAppCompatActivity;
import com.aliex.devkit.databinding.ActivityDetailBinding;
import com.aliex.devkit.model.ImageInfo;
import com.aliex.devkit.utils.BindingUtils;
import com.aliex.devkit.utils.ViewUtil;
import com.apt.annotation.apt.Extra;
import com.apt.annotation.apt.Router;
import com.apt.annotation.apt.SceneTransition;

@Router(Const.ARTICLE)
public class ArticleActivity extends BaseAppCompatActivity<ArticlePresenter, ActivityDetailBinding>
        implements ArticleContract.View, View.OnClickListener {
    @Extra(Const.HEAD_DATA)
    public ImageInfo mArticle;
    @SceneTransition(Const.TRANSLATE_VIEW)
    public ImageView image;

    @Override
    protected void initTransitionView() {
        image = mViewBinding.image;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public void initView() {
        BindingUtils.loadImg(mViewBinding.image, mArticle.image);
        setTitle(mArticle.title);
        mViewBinding.lvComment.setHeadData(mArticle);
        mPresenter.initAdapterPresenter(mViewBinding.lvComment.getPresenter(), mArticle);
    }

    @Override
    public void commentSuc() {
        mViewBinding.etComment.setText("");
        mViewBinding.lvComment.reFetch();
        Snackbar.make(mViewBinding.fab, "评论成功!", Snackbar.LENGTH_LONG).show();
        ViewUtil.hideKeyboard(this);
    }

    @Override
    public void commentFail() {
        Snackbar.make(mViewBinding.fab, "评论失败!", Snackbar.LENGTH_LONG).show();
    }

    public void onClick(View view) {
        String comment = mViewBinding.etComment.getText().toString();
        if (TextUtils.isEmpty(comment))
            Snackbar.make(mViewBinding.fab, "评论不能为空!", Snackbar.LENGTH_LONG).show();
    }
}
