package com.upasthit.ui.details

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.upasthit.R
import com.upasthit.data.model.local.db.tables.Timing
import com.upasthit.ui.base.RecyclerBaseAdapter
import com.upasthit.ui.base.RecyclerViewHolder

class SchoolTimingAdapter(context: Context, list: MutableList<Timing>) : RecyclerBaseAdapter() {
    var mDataList: MutableList<Timing> = ArrayList()
    var clickedPosition = MutableLiveData<Int>()
    var isListViewSelected = true
    private var context: Context? = null

    init {
        this.mDataList = list
        this.context = context
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return if (isListViewSelected) R.layout.item_timing_list
        else R.layout.item_student_grid
    }

    override fun getDataModel(position: Int): Any? = mDataList[position]

    override fun <T> updateData(mData: List<T>) {
        val data: Collection<Timing> = mData as Collection<Timing>
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