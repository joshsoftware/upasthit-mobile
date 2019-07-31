package com.upasthit.data.model.local.db.tables

import android.os.Parcelable
import io.realm.RealmObject
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Timing(
        var close_time: String? = null,
        var day: String? = null,
        var reminder_time: String? = null,
        var start_time: String? = null
) : RealmObject(), Parcelable