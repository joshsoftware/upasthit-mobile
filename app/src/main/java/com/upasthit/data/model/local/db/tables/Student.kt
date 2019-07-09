package com.upasthit.data.model.local.db.tables

import io.realm.RealmObject

open class Student(
        var name: String? = null,
        var registration_no: String? = null,
        var roll_no: Int? = null
) : RealmObject()