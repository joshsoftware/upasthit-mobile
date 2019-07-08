package com.upasthit.ui.selectclass

import androidx.lifecycle.ViewModelProviders
import com.upasthit.BR
import com.upasthit.R
import com.upasthit.databinding.ActivityClassSelectionBinding
import com.upasthit.ui.base.BaseActivity

class ClassSelectionActivity : BaseActivity<ActivityClassSelectionBinding, ClassSelectionViewModel>() {

    init {
        mToolbarRequired = false
    }

    override fun getToolbarTitle(): String? {
        return ""
    }

    override fun getLayoutId(): Int = R.layout.activity_class_selection

    override fun getViewModel(): ClassSelectionViewModel {
        return ViewModelProviders.of(this).get(ClassSelectionViewModel::class.java)
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun init() {
    }

    override fun initLiveDataObservables() {
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startBackAnimation(this@ClassSelectionActivity)
    }
}
