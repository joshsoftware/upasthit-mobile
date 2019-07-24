package com.upasthit.data.model.local.db;

import com.upasthit.data.model.local.db.tables.School;
import com.upasthit.data.model.local.db.tables.Staff;
import com.upasthit.data.model.local.db.tables.Standard;
import com.upasthit.data.model.local.db.tables.Student;
import com.upasthit.data.model.local.db.tables.SyacUpApiResponse;
import com.upasthit.data.model.local.db.tables.Timing;

import io.realm.annotations.RealmModule;


@RealmModule(classes = {SyacUpApiResponse.class, Student.class, Standard.class, Staff.class, School.class, Timing.class})
public class DataBaseModule {
}
