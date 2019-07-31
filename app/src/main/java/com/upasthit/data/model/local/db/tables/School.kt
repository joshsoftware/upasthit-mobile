package com.upasthit.data.model.local.db.tables

import android.os.Parcelable
import io.realm.RealmList
import io.realm.RealmObject
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
open class School(
        var id: String? = null,
        var name: String? = null,
        var school_code: String? = null,
        var contact_number: String? = null,
        var email: String? = null,
        var timings: @RawValue RealmList<Timing>? = null

) : RealmObject(), Parcelable