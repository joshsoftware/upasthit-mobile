package com.upasthit.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.android.gms.common.api.GoogleApiClient
import com.upasthit.BR
import com.upasthit.R
import com.upasthit.data.model.api.response.SyncUpApiResponse
import com.upasthit.data.model.local.db.tables.Staff
import com.upasthit.databinding.ActivityLoginBinding
import com.upasthit.ui.base.BaseActivity
import com.upasthit.ui.selectclass.ClassSelectionActivity
import com.upasthit.util.ActivityManager
import com.upasthit.util.NetworkUtilities
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    private var mCredentialsApiClient: GoogleApiClient? = null
    private val RC_HINT = 2

    override fun navigateToNextScreen() {

    }

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

        phoneSelectorDialog()

        buttonSignIn.setOnClickListener {
            checkForMobileNoPresentInLocalDatabase(textInputEditTextMobileNo.text.toString(), textInputEditTextPin.text.toString())
        }
    }

    private fun checkForMobileNoPresentInLocalDatabase(mobileNumber: String, pin: String) {

        //check mobile number in local database
        val realm = mViewModel.mDatabaseRealm.realmInstance
        val mStaff = realm.where(Staff::class.java).equalTo("mobile_number", mobileNumber).findFirst()

        if (mStaff != null) {
            switchToNextScreen(mStaff)
        } else {
            if (NetworkUtilities.isInternet(this).not()) {
                showToast(getString(R.string.error_no_internet_connection))
                return
            }
            mViewModel.getSchoolDetails(mobileNumber, pin)
        }
    }

    override fun initLiveDataObservables() {
        mViewModel.getSchoolDetailsResponse().observe(this, loginResponseObserver)
    }

    private val loginResponseObserver: Observer<SyncUpApiResponse> = Observer {
        checkForMobileNoPresentInLocalDatabase(textInputEditTextMobileNo.text.toString(), textInputEditTextPin.text.toString())
    }

    private fun switchToNextScreen(mStaff: Staff) {

        val bundle = Bundle()
        bundle.putString("mobile_number", mStaff.mobile_number)
        bundle.putString("pin", mStaff.pin)

        ActivityManager.startActivityWithBundle(this@LoginActivity, ClassSelectionActivity::class.java, bundle)
        startFwdAnimation(this@LoginActivity)
        finish()
    }

    private fun phoneSelectorDialog() {
        mCredentialsApiClient = GoogleApiClient.Builder(this).addApi(Auth.CREDENTIALS_API).build()
        return requestHint()
    }

    private fun requestHint() {

        val hintRequest = HintRequest.Builder().setPhoneNumberIdentifierSupported(true).build()
        try {
            val googleApiClient = GoogleApiClient.Builder(this).addApi(Auth.CREDENTIALS_API).build()
            val pendingIntent = Auth.CredentialsApi.getHintPickerIntent(googleApiClient, hintRequest)
            startIntentSenderForResult(pendingIntent.intentSender, RC_HINT, null, 0, 0, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        textInputEditTextMobileNo.setText(mViewModel.getSelectorPhoneNumber(requestCode, resultCode, data, RC_HINT))
    }
}
