package com.upasthit.ui.absentstudent

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Parcelable
import android.provider.Settings
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.upasthit.BR
import com.upasthit.R
import com.upasthit.data.model.api.CreateAttendanceResponse
import com.upasthit.data.model.api.request.CreateAttendanceRequest
import com.upasthit.data.model.local.db.tables.School
import com.upasthit.data.model.local.db.tables.Student
import com.upasthit.databinding.ActivityAbsentStudentBinding
import com.upasthit.ui.base.BaseActivity
import com.upasthit.util.AppAndroidUtils
import com.upasthit.util.ApplicationConstant
import com.upasthit.util.NetworkUtilities
import kotlinx.android.synthetic.main.activity_absent_student.*
import pub.devrel.easypermissions.EasyPermissions
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AbsentStudentActivity : BaseActivity<ActivityAbsentStudentBinding, AbsentStudentViewModel>(), EasyPermissions.PermissionCallbacks, AppAndroidUtils.OnAlertDialogSelectListener {

    private val EP_SMS_PERMISSION = 1

    override fun navigateToNextScreen() {

    }

    var isListViewSelected = true
    private lateinit var mAbsentStudentAdapter: AbsentStudentAdapter
    private var absentStudentList = ArrayList<Student>()

    override fun getToolbarTitle(): String? {
        return "Absent student"
    }

    override fun getLayoutId(): Int = com.upasthit.R.layout.activity_absent_student

    override fun getViewModel(): AbsentStudentViewModel {
        return ViewModelProviders.of(this).get(AbsentStudentViewModel::class.java)
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun init() {

        val mobileNumber = intent.extras.getString("mobile_number")
        val selectedStandard = intent.extras.getString("selectedStandardWithSection")
        val standardId = intent.extras.getString("standardId")

        //fetch school details
        val realm = mViewModel.mDatabaseRealm.realmInstance
        val mSchool = realm.where(School::class.java).findFirst()
        setToolbarTitle(mSchool?.name!!)

        textViewClassSection.text = "Class $selectedStandard"

        val simpleDateFormatForDayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault())
        val simpleDateFormatForDate = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val date = Date()
        val dayOfTheWeek = simpleDateFormatForDayOfWeek.format(date)
        val todayDate = simpleDateFormatForDate.format(date)

        textViewDate.text = todayDate
        textViewDay.text = dayOfTheWeek

        mAbsentStudentAdapter = recyclerViewAbsentStudents.adapter as AbsentStudentAdapter
        if (intent.extras != null) {
            absentStudentList = intent?.extras!!.getParcelableArrayList<Parcelable>(ApplicationConstant.ABSENT_STUDENT_DATA) as ArrayList<Student>
            mViewModel.setAbsentStudentList(absentStudentList)
            textViewAbsentStudentCount.text = absentStudentList.size.toString()
            textViewStudentAbsentTitle.text = "Student(s) Absent"
        }

        //List and grid view change click
        imageViewListViewType.setOnClickListener {
            if (isListViewSelected) {
                isListViewSelected = false
                imageViewListViewType.setImageResource(com.upasthit.R.drawable.ic_view_list)
                constrainListTitle.visibility = View.GONE
                recyclerViewAbsentStudents.layoutManager = GridLayoutManager(this@AbsentStudentActivity, AppAndroidUtils.calculateNoOfColumns(this@AbsentStudentActivity, 60f))
            } else {
                isListViewSelected = true
                imageViewListViewType.setImageResource(com.upasthit.R.drawable.ic_view_grid)
                constrainListTitle.visibility = View.VISIBLE
                recyclerViewAbsentStudents.layoutManager = LinearLayoutManager(this@AbsentStudentActivity)
            }
            mAbsentStudentAdapter.setLayoutFromFlag(isListViewSelected)
        }

        textViewConfirm.setOnClickListener {

            if (NetworkUtilities.isInternet(this)) {
                val attendanceIds = ArrayList<Int>()
                absentStudentList.forEach {
                    attendanceIds.add(2)
                }
                val mCreateAttendanceRequest = CreateAttendanceRequest(selectedStandard.substring(0, 1), selectedStandard.substring(2, 3), "1000", todayDate, attendanceIds)
                mViewModel.createAttendanceRequest(mCreateAttendanceRequest)
            } else {
                val permission = arrayOf(Manifest.permission.SEND_SMS)

                if (EasyPermissions.hasPermissions(this, *permission)) {
                    mViewModel.sendMessage("8956016201", "Test message")
                } else {
                    EasyPermissions.requestPermissions(this, getString(R.string.label_sms_permission), EP_SMS_PERMISSION, *permission)
                }
            }
        }
    }

    override fun initLiveDataObservables() {
        mViewModel.getAttendanceResponse().observe(this, attendanceObserver)
        mViewModel.getSendMessageResponse().observe(this, sendMessageObserver)
    }

    private val attendanceObserver: Observer<CreateAttendanceResponse> = Observer {
        showToast(it.message)
        finish()
    }

    private val sendMessageObserver: Observer<String> = Observer {
        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        AppAndroidUtils.openAlertDialog(this,
                getString(R.string.title_sms_permission_settings),
                getString(R.string.label_sms_permission_settings),
                "SETTINGS", "", this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        mViewModel.sendMessage("8956016201", "Test message")
    }

    override fun onPositiveButtonClick() {
        openSettings()
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }
}
