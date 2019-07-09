package com.upasthit.data.model.local.db.tables

import io.realm.RealmList
import io.realm.RealmObject

open class Standard(
        var id: Int? = null,
        var section: String? = null,
        var standard: String? = null,
        var start_time: String? = null,
        var students: RealmList<Student>? = null
) : RealmObject()