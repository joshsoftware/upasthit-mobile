package com.upasthit.data.model.local.db.tables

import io.realm.RealmList
import io.realm.RealmObject

open class School(
        var id: Int? = null,
        var name: String? = null,
        var school_code: String? = null,
        var contact_number: String? = null,
        var email: String? = null,
        var timings: RealmList<Timing>? = null
) : RealmObject(){}