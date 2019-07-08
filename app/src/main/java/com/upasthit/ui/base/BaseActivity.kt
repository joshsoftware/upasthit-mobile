package com.upasthit.ui.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.upasthit.R
import com.upasthit.custom.CustomProgressDialog
import com.upasthit.data.model.local.pref.AppPreferenceStorage


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity() {

    private var spinningDialog: Dialog? = null

    var mViewDataBinding: T? = null

    protected lateinit var mViewModel: V

    /**
     * To check activity needs a toolbar (if not then need to specify as false)
     */
    protected var mToolbarRequired: Boolean = true

    /**
     * TO manage visibility for navigation button
     */
    protected var mNavigationButtonRequired: Boolean = true
    /**
     * To handle actions on toolbar
     */
    lateinit var mToolbar: Toolbar
    lateinit var mToolbarTitle: TextView

    /**
     * This method is used to define toolbar title
     */
    abstract fun getToolbarTitle(): String?

    /**
     * This method is used to define layout id (Ex: R.layout.activity_login)
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * Override for set view model
     * @return view model instance
     */
    abstract fun getViewModel(): V

    /**
     * Override for set binding variable
     * @return variable id
     */
    abstract fun getBindingVariable(): Int

    /**
     * This method will be the starting point in all the other activities.
     */
    abstract fun init()

    /**
     * To initialise live data observables
     */
    abstract fun initLiveDataObservables()
//    protected abstract fun getToolbarRes(): Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        //toolbar
        initializeToolbar()
        init() // In all activities, this method will be called first.
        initLiveDataObservables()
        mViewModel.getProgress().observe(this, progressObserver)
        mViewModel.getMessage().observe(this, messageObserver)
        mViewModel.getAuthorizationFailedListener().observe(this, authorizationFailedObserver)
    }

    private fun initializeToolbar() {
        //set toolbar
        if (mToolbarRequired) {
            setToolBar()
        }
    }

    /**
     * To perform data binding operation
     */
    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        this.mViewModel = if (!::mViewModel.isInitialized) getViewModel() else mViewModel
        mViewDataBinding!!.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding!!.executePendingBindings()
    }

    /**
     * To set tool bar
     */
    fun setToolBar() {
        mToolbar = mViewDataBinding!!.root.findViewById(R.id.toolbar)

        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        if (mNavigationButtonRequired) {
            mToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
            mToolbar.setNavigationOnClickListener { onBackPressed() }
        } else {
            mToolbar.navigationIcon = null
            mToolbar.setNavigationOnClickListener(null)
        }

        if (getToolbarTitle() != null) {
            mToolbarTitle = mToolbar.findViewById(R.id.textViewToolbarTitle) as TextView
            mToolbarTitle.text = getToolbarTitle()
        }
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    protected fun getUserData() {
        val UserInfo = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
    }

    protected fun handleAuthorizationFailed() {

        try {
            val userInfo = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
            userInfo.edit().clear().apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        /**
         * Clear all preference storage
         */
        AppPreferenceStorage.removePreference()

//        //TODO change to optimized way to make authentication
//        try {
//            val i = Intent(this@BaseActivity, LoginActivity::class.java)
//            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(i)
//            finish()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }

//        try {
//            val gcmToken = PreferenceManager.getDefaultSharedPreferences(applicationContext)
//            gcmToken.edit().clear().apply()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }

//        try {
//            val intent = Intent(this, LoginActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//            startActivity(intent)
//            finish()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }


    /**
     * To load progress dialog on screen
     */
    fun showLoader() {
        try {
            if (spinningDialog == null) {
                spinningDialog = CustomProgressDialog.showProgressDialog(this@BaseActivity)
            }
            spinningDialog!!.setCancelable(false)
            spinningDialog!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * To hide showing dialog
     */
    fun hideLoader() {
        spinningDialog?.let { if (it.isShowing) it.cancel() }
    }

    /**
     * To hide toolbar
     */
    fun hideToolbar() {
        if (::mToolbar.isInitialized)
            mToolbar.visibility = View.GONE
    }

    /**
     * To display toolbar
     */
    fun showToolbar() {
        if (::mToolbar.isInitialized)
            mToolbar.visibility = View.VISIBLE
    }

    /**
     * To hide keyboard from screen
     */
    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * To set Toolbar title
     */
    fun setToolbarTitle(toolBarTitle: String) {
        mToolbarTitle.text = toolBarTitle
    }

    fun showToast(message: String?, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }

    fun startFwdAnimation(activity: Activity) {
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    fun startBackAnimation(activity: Activity) {
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    fun startPopAnimation(activity: Activity) {
        activity.overridePendingTransition(R.anim.popup_show, R.anim.popup_hide)
    }

    fun startFadeInAnimation(activity: Activity) {
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    /**
     * To handle error
     */
    protected val messageObserver: Observer<String> = Observer<String> { t ->
        //        Logger.e(BaseFragment::class.java, t.toString())
        showToast(t.toString())
    }
    private val progressObserver: Observer<Boolean> = Observer<Boolean> {
        if (it!!) {
            hideKeyboard()
            showLoader()
        } else
            hideLoader()
    }

    protected val authorizationFailedObserver: Observer<Boolean> = Observer {
        handleAuthorizationFailed()
    }
}