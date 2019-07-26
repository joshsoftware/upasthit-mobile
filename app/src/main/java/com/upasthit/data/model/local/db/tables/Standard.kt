package com.upasthit.data.model.local.db.tables

import io.realm.RealmList
import io.realm.RealmObject

open class Standard(
        var id: String? = null,
        var section: String? = null,
        var standard: String? = null,
        var students: RealmList<Student>? = null

) : RealmObject() {

    override fun toString(): String {
        return "Standard $standard Section $section"
    }
}