package com.upasthit.data.model.api.request

class CreateAttendanceRequest(
        val standard: String,
        val section: String,
        val school_code: String,
        val date: String,
        val absent_roll_nos: ArrayList<Int>
)