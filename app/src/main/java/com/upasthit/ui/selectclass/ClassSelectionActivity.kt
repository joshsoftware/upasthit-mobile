package com.upasthit.ui.selectclass

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProviders
import com.upasthit.BR
import com.upasthit.R
import com.upasthit.data.model.local.db.tables.School
import com.upasthit.data.model.local.db.tables.Staff
import com.upasthit.data.model.local.db.tables.Standard
import com.upasthit.databinding.ActivityClassSelectionBinding
import com.upasthit.ui.base.BaseActivity
import com.upasthit.ui.home.HomeActivity
import com.upasthit.util.ActivityManager
import kotlinx.android.synthetic.main.activity_class_selection.*

class ClassSelectionActivity : BaseActivity<ActivityClassSelectionBinding, ClassSelectionViewModel>() {


    private val standards = ArrayList<Standard>()

    override fun navigateToNextScreen() {

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
        val mobileNumber = intent?.extras?.getString("mobile_number")
        val pin = intent?.extras?.getString("pin")

        val realm = mViewModel.mDatabaseRealm.realmInstance

        //fetch school details
        val mSchool = realm.where(School::class.java).findFirst()
        setToolbarTitle(mSchool?.name!!)

        //fetch staff details
        val mStaff = realm.where(Staff::class.java).equalTo("mobile_number", mobileNumber).findFirst()

        if (mStaff != null) {

            textViewTeacherName.text = mStaff.first_name + " " + mStaff.last_name

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

            val bundle = Bundle()
            bundle.putString("mobile_number", mStaff?.mobile_number)
            bundle.putString("pin", pin)
            bundle.putString("standardId", standards[spinnerSection.selectedItemPosition].id)

            val selectedStandardWithSection = spinnerClass.selectedItem.toString().replace("Standard ", "").replace(" Section ", "-").trim()
            bundle.putString("selectedStandardWithSection", selectedStandardWithSection)

            ActivityManager.startActivityWithBundle(this@ClassSelectionActivity, HomeActivity::class.java, bundle)
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
