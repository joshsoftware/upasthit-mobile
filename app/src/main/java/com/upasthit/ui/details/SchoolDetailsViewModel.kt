package com.upasthit.ui.details

import com.upasthit.BR
import com.upasthit.data.model.local.db.tables.Timing
import com.upasthit.ui.base.BaseViewModel
import io.realm.RealmList

class SchoolDetailsViewModel : BaseViewModel() {

    fun setTimingList(timings: RealmList<Timing>?) {

        mDataList.clear()
        mDataList.addAll(ArrayList(timings))
        notifyPropertyChanged(BR._all)
    }

    var mDataList: MutableList<Timing> = ArrayList()
}