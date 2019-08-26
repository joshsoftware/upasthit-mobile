package com.upasthit.ui.login

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.upasthit.BR
import com.upasthit.R
import com.upasthit.data.model.local.db.tables.Staff
import com.upasthit.databinding.ActivityLoginBinding
import com.upasthit.ui.base.BaseActivity
import com.upasthit.ui.selectclass.ClassSelectionActivity
import com.upasthit.util.ActivityManager
import kotlinx.android.synthetic.main.activity_login.buttonSignIn
import kotlinx.android.synthetic.main.activity_verify_pin.*

class VerifyPinActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    override fun navigateToNextScreen() {

    }

    init {
        mToolbarRequired = false
    }

    override fun getToolbarTitle(): String? {
        return "Login"
    }

    override fun getLayoutId(): Int = R.layout.activity_verify_pin

    override fun getViewModel(): LoginViewModel {
        return ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun init() {

        val mobileNumber = intent?.extras?.getString("mobile_number")

        buttonSignIn.setOnClickListener {

            //Verify pin
            val realm = mViewModel.mDatabaseRealm.realmInstance
            val mStaff = realm.where(Staff::class.java).equalTo("mobile_number", mobileNumber).findFirst()

            if (mStaff?.pin == textInputEditTextPin.text.toString()) {

                val bundle = Bundle()
                bundle.putString("mobile_number", mStaff.mobile_number)

                ActivityManager.startActivityWithBundle(this, ClassSelectionActivity::class.java, bundle)
                startFwdAnimation(this)
                finish()
            } else {
                showToast("Wrong Pin, Please contact to school admin")
            }
        }
    }

    override fun initLiveDataObservables() {
    }
}
