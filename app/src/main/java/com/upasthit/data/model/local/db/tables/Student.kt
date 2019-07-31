package com.upasthit.data.model.local.db.tables

import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.Ignore
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Student(
        var first_name: String? = null,
        var last_name: String? = null,
        var registration_no: String? = null,
        var roll_no: String? = null,
        @Ignore var isSelected: Boolean? = false

) : RealmObject(), Parcelable
