package com.upasthit.ui.selectclass

import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProviders
import com.upasthit.BR
import com.upasthit.R
import com.upasthit.data.model.local.db.tables.Staff
import com.upasthit.data.model.local.db.tables.Standard
import com.upasthit.databinding.ActivityClassSelectionBinding
import com.upasthit.ui.base.BaseActivity
import com.upasthit.ui.home.HomeActivity
import com.upasthit.util.ActivityManager
import kotlinx.android.synthetic.main.activity_class_selection.*

class ClassSelectionActivity : BaseActivity<ActivityClassSelectionBinding, ClassSelectionViewModel>() {

    override fun navigateToNextScreen() {

    }

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

        //Fetch standard 7 section details
        val mobileNumber = intent.extras.getString("mobile_number")

        val realm = mViewModel.mDatabaseRealm.realmInstance
        val mStaff = realm.where(Staff::class.java).equalTo("mobile_number", mobileNumber).findFirst()

        if (mStaff != null) {

            textViewTeacherName.text = mStaff.first_name + " " + mStaff.last_name

            val standards = ArrayList<Standard>()

            val standardIdFromDb = realm.where(Staff::class.java).equalTo("mobile_number", mobileNumber).findFirst()?.standard_ids


            standardIdFromDb?.forEach {
                val standard = realm.where(Standard::class.java).equalTo("id", it.toString()).findFirst()

                if (standard != null) {
                    standards.add(standard)
                }
            }

            val standardListArrayAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, standards)
            spinnerClass.adapter = standardListArrayAdapter

            mStaff.standard_ids
        }

        bottomLayout.setOnClickListener {
            ActivityManager.startActivity(this@ClassSelectionActivity, HomeActivity::class.java)
            startFwdAnimation(this@ClassSelectionActivity)
        }
    }

    override fun initLiveDataObservables() {
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startBackAnimation(this@ClassSelectionActivity)
    }
}
