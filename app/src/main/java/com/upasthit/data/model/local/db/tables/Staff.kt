package com.upasthit.data.model.local.db.tables

import io.realm.RealmList
import io.realm.RealmObject

open class Staff(
        var designation: String? = null,
        var id: Int? = null,
        var mobile_number: String? = null,
        var name: String? = null,
        var pin: String? = null,
        var registration_no: String? = null,
        var standard_ids: RealmList<String>? = null
) : RealmObject()