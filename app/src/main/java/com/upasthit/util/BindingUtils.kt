package com.upasthit.util

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.upasthit.R
import com.upasthit.data.model.local.db.tables.Student
import com.upasthit.ui.absentstudent.AbsentStudentAdapter
import com.upasthit.ui.home.StudentsAdapter

object BindingUtils {

    @JvmStatic
    @BindingAdapter("studentList")
    fun setStudentList(recyclerView: RecyclerView, list: MutableList<Student>) {
        if (recyclerView.adapter != null) {
            val mAdapter = recyclerView.adapter as StudentsAdapter
            mAdapter.updateData(list)
        } else {
            val taskAdapter = StudentsAdapter(recyclerView.context, list)
//            recyclerView.addItemDecoration(ItemOffsetDecoration(recyclerView.context, R.dimen.margin_5))
            recyclerView.adapter = taskAdapter
        }
    }

    @BindingAdapter("studentItemSelectionStatus")
    @JvmStatic
    fun setStudentItemSelectionStatus(layout: ConstraintLayout, isSelected: Boolean?) {
        if (isSelected!!) {
            layout.setBackgroundResource(R.drawable.hollow_rounded_box_dark_blue)
        } else {
            layout.setBackgroundResource(R.drawable.hollow_rounded_box)
        }
    }

    @JvmStatic
    @BindingAdapter("absentStudentList")
    fun setAbsentStudentList(recyclerView: RecyclerView, list: MutableList<Student>) {
        if (recyclerView.adapter != null) {
            val mAdapter = recyclerView.adapter as AbsentStudentAdapter
            mAdapter.updateData(list)
        } else {
            val taskAdapter = AbsentStudentAdapter(recyclerView.context, list)
//            recyclerView.addItemDecoration(ItemOffsetDecoration(recyclerView.context, R.dimen.margin_5))
            recyclerView.adapter = taskAdapter
        }
    }
}