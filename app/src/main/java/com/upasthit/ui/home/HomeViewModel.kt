package com.upasthit.ui.home

import androidx.databinding.Bindable
import com.upasthit.BR
import com.upasthit.data.model.local.db.tables.Student
import com.upasthit.ui.login.LoginViewModel
import java.util.*

class HomeViewModel : LoginViewModel() {

    var mDataList: MutableList<Student> = ArrayList()

    @Bindable
    fun getStudentList(): List<Student> {
        return mDataList
    }

    fun setStudentList(studentList: MutableList<Student>) {
        mDataList.clear()
        mDataList.addAll(studentList)
        notifyPropertyChanged(BR._all)
    }

    init {
        mDataList.add(Student())
        mDataList.add(Student())
        mDataList.add(Student())
        notifyPropertyChanged(BR._all)
    }

    fun getSelectedStudentList(): ArrayList<Student> {
        val students = ArrayList<Student>()
        for (i in 0 until mDataList.size) {
            if (mDataList[i].isSelected!!) {
                students.add(mDataList[i])
            }
        }
        return students
    }
}