package com.upasthit.data.model.local.db.tables

import io.realm.RealmObject

open class Timing(
        var close_time: String? = null,
        var day: String? = null,
        var reminder_time: String? = null,
        var start_time: String? = null
) : RealmObject()