package com.upasthit.ui.absentstudent

import android.os.Parcelable
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.upasthit.BR
import com.upasthit.R
import com.upasthit.data.model.local.db.tables.Student
import com.upasthit.databinding.ActivityAbsentStudentBinding
import com.upasthit.ui.base.BaseActivity
import com.upasthit.util.AppAndroidUtils
import com.upasthit.util.ApplicationConstant
import kotlinx.android.synthetic.main.activity_absent_student.*

class AbsentStudentActivity : BaseActivity<ActivityAbsentStudentBinding, AbsentStudentViewModel>() {
    override fun navigateToNextScreen() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var isListViewSelected = true
    private lateinit var mAbsentStudentAdapter: AbsentStudentAdapter

    override fun getToolbarTitle(): String? {
        return "Absent student"
    }

    override fun getLayoutId(): Int = R.layout.activity_absent_student

    override fun getViewModel(): AbsentStudentViewModel {
        return ViewModelProviders.of(this).get(AbsentStudentViewModel::class.java)
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun init() {
        mAbsentStudentAdapter = recyclerViewAbsentStudents.adapter as AbsentStudentAdapter
        if (intent.extras != null) {
            val absentStudentList = intent?.extras!!.getParcelableArrayList<Parcelable>(ApplicationConstant.ABSENT_STUDENT_DATA) as ArrayList<Student>
            mViewModel.setAbsentStudentList(absentStudentList)
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
    }

    override fun initLiveDataObservables() {
    }
}
