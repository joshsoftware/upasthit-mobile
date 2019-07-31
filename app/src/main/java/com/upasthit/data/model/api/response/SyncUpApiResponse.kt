package com.upasthit.data.model.api.response

import android.os.Parcelable
import com.upasthit.data.model.local.db.tables.School
import com.upasthit.data.model.local.db.tables.Staff
import com.upasthit.data.model.local.db.tables.Standard
import io.realm.RealmList
import io.realm.RealmObject
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
open class SyncUpApiResponse(
        var school: School? = null,
        var staff: @RawValue RealmList<Staff>? = null,
        var standard: @RawValue RealmList<Standard>? = null

) : RealmObject(), Parcelable