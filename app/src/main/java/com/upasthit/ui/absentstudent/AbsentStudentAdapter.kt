package com.upasthit.ui.absentstudent

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.upasthit.ui.base.RecyclerViewHolder
import com.upasthit.R
import com.upasthit.data.model.local.db.tables.Student
import com.upasthit.ui.base.RecyclerBaseAdapter

class AbsentStudentAdapter(context: Context, list: MutableList<Student>) : RecyclerBaseAdapter() {
    var mDataList: MutableList<Student> = ArrayList()
    var clickedPosition = MutableLiveData<Int>()
    var isListViewSelected = true
    private var context: Context? = null

    init {
        this.mDataList = list
        this.context = context
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return if (isListViewSelected) R.layout.item_absent_student_list
        else R.layout.item_absent_student_grid
    }

    override fun getDataModel(position: Int): Any? = mDataList[position]

    override fun <T> updateData(mData: List<T>) {
        val data: Collection<Student> = mData as Collection<Student>
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.root.setOnClickListener {
            //Play chef profile training video from final step
            clickedPosition.postValue(position)
        }
    }

    fun getClickedItemPosition(): MutableLiveData<Int> {
        return clickedPosition
    }


    override fun getItemCount(): Int {
        if (mDataList != null && mDataList?.size!! > 0) {
            return mDataList?.size!!
        } else {
            return 0
        }
    }

    fun setLayoutFromFlag(viewSelected: Boolean) {
        isListViewSelected = viewSelected
        notifyDataSetChanged()
    }
}