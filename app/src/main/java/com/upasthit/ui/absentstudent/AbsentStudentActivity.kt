package com.upasthit.ui.absentstudent

import android.os.Parcelable
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
import kotlinx.android.synthetic.main.activity_absent_student.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AbsentStudentActivity : BaseActivity<ActivityAbsentStudentBinding, AbsentStudentViewModel>() {

    override fun navigateToNextScreen() {

    }

    var isListViewSelected = true
    private lateinit var mAbsentStudentAdapter: AbsentStudentAdapter
    private var absentStudentList = ArrayList<Student>()

    override fun getToolbarTitle(): String? {
        return "Absent student"
    }

    override fun getLayoutId(): Int = R.layout.activity_absent_student

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
                imageViewListViewType.setImageResource(R.drawable.ic_view_list)
                constrainListTitle.visibility = View.GONE
                recyclerViewAbsentStudents.layoutManager = GridLayoutManager(this@AbsentStudentActivity, AppAndroidUtils.calculateNoOfColumns(this@AbsentStudentActivity, 60f))
            } else {
                isListViewSelected = true
                imageViewListViewType.setImageResource(R.drawable.ic_view_grid)
                constrainListTitle.visibility = View.VISIBLE
                recyclerViewAbsentStudents.layoutManager = LinearLayoutManager(this@AbsentStudentActivity)
            }
            mAbsentStudentAdapter.setLayoutFromFlag(isListViewSelected)
        }

        textViewConfirm.setOnClickListener {

            val attendanceIds = ArrayList<Int>()
            absentStudentList.forEach {
                attendanceIds.add(2)
            }

            val mCreateAttendanceRequest = CreateAttendanceRequest(selectedStandard.substring(0, 1), selectedStandard.substring(2, 3), "1000", todayDate, attendanceIds)

            mViewModel.createAttendanceRequest(mCreateAttendanceRequest)
        }
    }

    override fun initLiveDataObservables() {
        mViewModel.getAttendanceResponse().observe(this, attendanceObserver)
    }

    private val attendanceObserver: Observer<CreateAttendanceResponse> = Observer {
        showToast(it.message)
//        ActivityManager.startFreshActivityClearStack(this@AbsentStudentActivity, HomeActivity::class.java)
//        startFwdAnimation(this@AbsentStudentActivity)
        finish()
    }
}
