package com.upasthit.ui.base

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer

abstract class BaseDialog<T : ViewDataBinding, V : BaseViewModel> : DialogFragment() {

    protected lateinit var mViewDataBinding: T
    protected lateinit var mViewModel: V
    private lateinit var mRootView: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val root = RelativeLayout(activity)
        root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        )

        // creating the fullscreen dialog
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val widthPixels = Resources.getSystem().displayMetrics.widthPixels
        dialog.setContentView(root)
        if (dialog.window != null) {
//            dialog.window!!.setBackgroundDrawableResource(R.drawable.dialog_background)
            dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        }
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        return dialog
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()

    }

    abstract fun getLayoutFile(): Int

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract fun getBindingVariable(): Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): V

    /**
     * To initialise data
     */
    abstract fun initData()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutFile(), container, false)
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding.executePendingBindings()
        mRootView = mViewDataBinding.root
        initData()
        return mRootView
    }

    /**
     * To handle error
     */
    protected val errorMessageObserver: Observer<String> = Observer<String> { t ->
        //        Logger.e(BaseFragment::class.java, t.toString())
        (activity as BaseActivity<*, *>).showToast(t.toString())
    }

    protected fun showLoader() {
        (activity as BaseActivity<*, *>).showLoader()
    }

    protected fun hideLoader() {
        (activity as BaseActivity<*, *>).hideLoader()
    }
}