package com.upasthit.ui.details

import androidx.lifecycle.ViewModelProviders
import com.upasthit.BR
import com.upasthit.R
import com.upasthit.data.model.local.db.tables.School
import com.upasthit.databinding.ActivityClassSelectionBinding
import com.upasthit.ui.base.BaseActivity

class SchoolDetailsActivity : BaseActivity<ActivityClassSelectionBinding, SchoolDetailsViewModel>() {

    override fun navigateToNextScreen() {

    }

    override fun getToolbarTitle(): String? {
        return ""
    }

    override fun getLayoutId(): Int = R.layout.activity_school_details

    override fun getViewModel(): SchoolDetailsViewModel {
        return ViewModelProviders.of(this).get(SchoolDetailsViewModel::class.java)
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun init() {

        //Fetch standard 7 section details
        val mobileNumber = intent.extras.getString("mobile_number")

        val realm = mViewModel.mDatabaseRealm.realmInstance

        //fetch school details
        val mSchool = realm.where(School::class.java).findFirst()
        setToolbarTitle(mSchool?.name!!)

        val timings = mSchool.timings
        mViewModel.setTimingList(timings)
    }

    override fun initLiveDataObservables() {
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startBackAnimation(this@SchoolDetailsActivity)
    }
}
