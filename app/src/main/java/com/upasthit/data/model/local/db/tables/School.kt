package com.upasthit.data.model.local.db.tables

import io.realm.RealmObject

open class School(
        var id: Int?= null,
        var name: String?= null,
        var school_code: String?= null,
        var start_time: String?= null
):RealmObject()