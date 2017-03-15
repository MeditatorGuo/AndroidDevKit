package com.aliex.devkit.ui.main;

import com.aliex.aptlib.TRouter;
import com.aliex.commonlib.utils.StatusBarUtil;
import com.aliex.devkit.event.EventBus;
import com.aliex.devkit.event.EventTags;
import com.aliex.commonlib.utils.AnimationUtils;
import com.aliex.devkit.Const;
import com.aliex.devkit.R;
import com.aliex.devkit.activity.DataBindingActivity;
import com.aliex.devkit.databinding.ActivityFlashBinding;
import com.apt.annotation.javassist.Bus;

import android.view.animation.AlphaAnimation;

/**
 * author: Aliex <br/>
 * created on: 2017/3/13 <br/>
 * description: <br/>
 */

public class SplashActivity extends DataBindingActivity<ActivityFlashBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_flash;
    }

    @Override
    public void initView() {
        EventBus.getInstance().onStickyEvent(EventTags.FLASH_INIT_UI);
    }

    @Bus(EventTags.FLASH_INIT_UI)
    public void initUI() {
        StatusBarUtil.setTranslucentBackground(this);
        AlphaAnimation anim = new AlphaAnimation(0.8f, 0.1f);
        anim.setDuration(5000);
        mViewBinding.view.startAnimation(anim);
        AnimationUtils.setAnimationListener(anim, () -> EventBus.getInstance().onEvent(EventTags.JUMP_TO_MAIN));
    }

    @Bus(EventTags.JUMP_TO_MAIN)
    public void jumpToMainPage() {
        TRouter.go(Const.HOME);
        finish();
    }
}
