package com.upasthit.ui.absentstudent

import com.upasthit.BR
import com.upasthit.data.model.local.db.tables.Student
import com.upasthit.ui.base.BaseViewModel

class AbsentStudentViewModel : BaseViewModel() {

    var mDataList: MutableList<Student> = ArrayList()

    fun setAbsentStudentList(absentStudentList: ArrayList<Student>) {
        mDataList.clear()
        mDataList.addAll(absentStudentList)
        notifyPropertyChanged(BR._all)
    }
}