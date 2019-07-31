package com.upasthit.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.upasthit.BR
import com.upasthit.R
import com.upasthit.data.model.api.response.SyncUpApiResponse
import com.upasthit.data.model.local.db.tables.School
import com.upasthit.data.model.local.db.tables.Staff
import com.upasthit.data.model.local.db.tables.Standard
import com.upasthit.data.model.local.db.tables.Student
import com.upasthit.databinding.ActivityHomeBinding
import com.upasthit.ui.absentstudent.AbsentStudentActivity
import com.upasthit.ui.base.BaseActivity
import com.upasthit.ui.details.SchoolDetailsActivity
import com.upasthit.ui.login.LoginActivity
import com.upasthit.util.ActivityManager
import com.upasthit.util.AppAndroidUtils
import com.upasthit.util.ApplicationConstant
import com.upasthit.util.NetworkUtilities
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.nav_header_home.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(), NavigationView.OnNavigationItemSelectedListener, AppAndroidUtils.OnAlertDialogSelectListener {

    override fun navigateToNextScreen() {
    }

    private var mobileNumber = ""
    private var isListViewSelected = true
    private lateinit var mStudentsAdapter: StudentsAdapter

    override fun getToolbarTitle(): String? {
        return "Student List"
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun getViewModel(): HomeViewModel {
        return ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun init() {

        mobileNumber = intent.extras.getString("mobile_number")
        val selectedStandard = intent.extras.getString("selectedStandardWithSection")
        val standardId = intent.extras.getString("standardId")

        textViewClassSection.text = "Class $selectedStandard"

        val simpleDateFormatForDayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault())
        val simpleDateFormatForDate = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val date = Date()
        val dayOfTheWeek = simpleDateFormatForDayOfWeek.format(date)
        val todayDate = simpleDateFormatForDate.format(date)

        textViewDate.text = todayDate
        textViewDay.text = dayOfTheWeek

        mNavigationButtonRequired = false
        mStudentsAdapter = recyclerViewStudents.adapter as StudentsAdapter

        setDrawerUserData(mobileNumber)

        val realm = mViewModel.mDatabaseRealm.realmInstance

        //fetch school details
        val mSchool = realm.where(School::class.java).findFirst()
        setToolbarTitle(mSchool?.name!!)

        //fetch student list
        val studentList = realm.where(Standard::class.java).equalTo("id", standardId).findFirst()?.students

        val interestList: MutableList<Student> = ArrayList<Student>()
        studentList?.forEach { t: Student? ->
            val student = Student(t?.first_name, t?.last_name, t?.registration_no, t?.roll_no, t?.isSelected)
            interestList.add(student)
        }
        mViewModel.setStudentList(interestList)
        mStudentsAdapter.notifyDataSetChanged()

        //List and grid view change click
        imageViewListViewType.setOnClickListener {
            if (isListViewSelected) {
                isListViewSelected = false
                imageViewListViewType.setImageResource(com.upasthit.R.drawable.ic_view_list)
                constrainListTitle.visibility = View.GONE
                recyclerViewStudents.layoutManager = GridLayoutManager(this@HomeActivity, AppAndroidUtils.calculateNoOfColumns(this@HomeActivity, 60f))
            } else {
                isListViewSelected = true
                imageViewListViewType.setImageResource(com.upasthit.R.drawable.ic_view_grid)
                constrainListTitle.visibility = View.VISIBLE
                recyclerViewStudents.layoutManager = LinearLayoutManager(this@HomeActivity)
            }
            mStudentsAdapter.setLayoutFromFlag(isListViewSelected)
        }

        menuRight.setOnClickListener {
            if (drawer_layout.isDrawerOpen(GravityCompat.END)) {
                drawer_layout.closeDrawer(GravityCompat.END)
            } else {
                drawer_layout.openDrawer(GravityCompat.END)
            }
        }
        nav_view.setNavigationItemSelectedListener(this)

        bottomLayout.setOnClickListener {
            if (mViewModel.getSelectedStudentList().isNotEmpty()) {

                val bundle = Bundle()
                bundle.putString("mobile_number", mobileNumber)
                bundle.putString("standardId", standardId)
                bundle.putString("selectedStandardWithSection", selectedStandard)
                bundle.putParcelableArrayList(ApplicationConstant.ABSENT_STUDENT_DATA, mViewModel.getSelectedStudentList())
                ActivityManager.startActivityForResultWithBundle(this@HomeActivity, AbsentStudentActivity::class.java, 1, bundle)
                startFwdAnimation(this@HomeActivity)

            } else {
                showToast("Please select absent student")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 1 && requestCode == 1) {
            setResult(1)
            finish()
        }
    }

    private fun setDrawerUserData(mobileNumber: String) {
        val header = nav_view.getHeaderView(0)

        //fetch staff details
        val realm = mViewModel.mDatabaseRealm.realmInstance
        val mStaff = realm.where(Staff::class.java).equalTo("mobile_number", mobileNumber).findFirst()

        header.textViewUserName.text = mStaff?.first_name + " " + mStaff?.last_name
        header.textViewUserMobile.text = mStaff?.mobile_number
    }

    override fun initLiveDataObservables() {
        mStudentsAdapter.getClickedItemPosition().observe(this, mClickPositionObserver)
        mViewModel.getSchoolDetailsResponse().observe(this, loginResponseObserver)
    }

    private val mClickPositionObserver = Observer<Int> {

        val interestList: MutableList<Student> = ArrayList<Student>()
        mViewModel.getStudentList().forEach { t: Student? ->
            val student = Student(t?.first_name, t?.last_name, t?.registration_no, t?.roll_no, t?.isSelected)
            interestList.add(student)
        }
        interestList[it].isSelected = !mViewModel.getStudentList()[it].isSelected!!
        mViewModel.setStudentList(interestList)
        mStudentsAdapter.notifyItemChanged(it)
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.END)) {
            drawer_layout.closeDrawer(GravityCompat.END)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_attendance_history -> {
                showToast("Attendance History Clicked")
            }
            R.id.nav_school_details -> {
                val bundle = Bundle()
                bundle.putString("mobile_number", mobileNumber)
                ActivityManager.startActivityWithBundle(this, SchoolDetailsActivity::class.java, bundle)
            }
            R.id.nav_sync_content -> {
                AppAndroidUtils.openAlertDialog(this,
                        getString(R.string.app_name), "Do you really want to sync school data ?", "YES", "Not Now", this)
            }
            R.id.nav_about_app -> {
                showToast("About Us Clicked")
            }
            R.id.nav_logout -> {
                AppAndroidUtils.openAlertDialog(this, getString(R.string.app_name), "Are you sure want to log out?", "YES", "Cancel", this)
            }
        }
        drawer_layout.closeDrawer(GravityCompat.END)
        return true
    }

    override fun onPositiveButtonClick() {
        if (NetworkUtilities.isInternet(this).not()) {
            showToast(getString(R.string.error_no_internet_connection))
            return
        }
        mViewModel.getSchoolDetails(mobileNumber)
    }

    private val loginResponseObserver: Observer<SyncUpApiResponse> = Observer {
        ActivityManager.startFreshActivityClearStack(this, LoginActivity::class.java)
        finish()
    }
}
