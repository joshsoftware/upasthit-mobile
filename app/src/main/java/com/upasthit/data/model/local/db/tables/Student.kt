package com.upasthit.data.model.local.db.tables

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.Ignore

open class Student(
        var first_name: String? = null,
        var last_name: String? = null,
        var registration_no: String? = null,
        var roll_no: String? = null,
        @Ignore var isSelected: Boolean? = false
) : RealmObject(), Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(first_name)
        parcel.writeString(last_name)
        parcel.writeString(registration_no)
        parcel.writeString(roll_no)
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
