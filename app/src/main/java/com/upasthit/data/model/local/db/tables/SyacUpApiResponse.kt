package com.upasthit.data.model.local.db.tables

import io.realm.RealmList
import io.realm.RealmObject

open class SyacUpApiResponse(
        var school: School? = null,
        var staff: RealmList<Staff>? = null,
        var standard: RealmList<Standard>? = null
) : RealmObject()