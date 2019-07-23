package com.upasthit.data.model.local.db.tables

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.Ignore

open class Student(
        var name: String? = null,
        var registration_no: String? = null,
        var roll_no: Int? = null,
        @Ignore var isSelected: Boolean? = false
) : RealmObject(), Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(registration_no)
        parcel.writeValue(roll_no)
        parcel.writeValue(isSelected)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Student> {
        override fun createFromParcel(parcel: Parcel): Student {
            return Student(parcel)
        }

        override fun newArray(size: Int): Array<Student?> {
            return arrayOfNulls(size)
        }
    }
}