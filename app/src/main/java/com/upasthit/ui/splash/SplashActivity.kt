package com.upasthit.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.ViewModelProviders
import com.upasthit.BR
import com.upasthit.R
import com.upasthit.databinding.ActivitySplashBinding
import com.upasthit.ui.base.BaseActivity
import com.upasthit.ui.login.LoginActivity
import com.upasthit.util.ActivityManager

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    init {
        mToolbarRequired = false
    }

    override fun getToolbarTitle(): String? {
        return ""
    }

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun getViewModel(): SplashViewModel {
        return ViewModelProviders.of(this).get(SplashViewModel::class.java)
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        selectNextScreen()
    }

    override fun initLiveDataObservables() {
    }

    private fun selectNextScreen() {

        Handler().postDelayed({
            //            if (AppPreferenceStorage.userLoggedIn!!) {
//                ActivityManager.startFreshActivityClearStack(this@SplashActivity, HomeActivity::class.java)
//            } else {
            ActivityManager.startFreshActivityClearStack(this@SplashActivity, LoginActivity::class.java)
//            }
//            startFadeInAnimation(this@SplashActivity)
            startFwdAnimation(this@SplashActivity)
        }, 1000)
    }
}
