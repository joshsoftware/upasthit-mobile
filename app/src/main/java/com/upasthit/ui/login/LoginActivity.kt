package com.upasthit.ui.login

import androidx.lifecycle.ViewModelProviders
import com.upasthit.BR
import com.upasthit.R
import com.upasthit.databinding.ActivityLoginBinding
import com.upasthit.ui.base.BaseActivity
import com.upasthit.ui.selectclass.ClassSelectionActivity
import com.upasthit.util.ActivityManager
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    init {
        mToolbarRequired = false
    }

    override fun getToolbarTitle(): String? {
        return "Login"
    }

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun getViewModel(): LoginViewModel {
        return ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun init() {
        buttonSignIn.setOnClickListener {
            ActivityManager.startActivity(this@LoginActivity, ClassSelectionActivity::class.java)
            startFwdAnimation(this@LoginActivity)
        }
    }

    override fun initLiveDataObservables() {
    }
}
