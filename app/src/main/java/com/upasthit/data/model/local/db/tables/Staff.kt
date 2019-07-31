package com.upasthit.data.model.local.db.tables

import android.os.Parcelable
import io.realm.RealmList
import io.realm.RealmObject
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
open class Staff(
        var designation: String? = null,
        var id: Int? = null,
        var mobile_number: String? = null,
        var pin: String? = null,
        var registration_no: String? = null,
        var standard_ids: @RawValue RealmList<String>? = null,
        var first_name: String? = null,
        var last_name: String? = null,
        var preferred_language: String? = null

) : RealmObject(), Parcelable