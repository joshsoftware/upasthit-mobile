package com.upasthit.data.network

import com.upasthit.data.model.api.CreateAttendanceResponse
import com.upasthit.data.model.api.request.CreateAttendanceRequest
import com.upasthit.data.model.local.db.tables.SyacUpApiResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NetworkServices {

    @GET("v1/staffs/sync")
    fun syncData(@Query("mobile_number") mobileNumber: String): Observable<Response<SyacUpApiResponse>>

    @POST("v1/attendances")
    fun createAttendance(@Body request: CreateAttendanceRequest): Observable<Response<CreateAttendanceResponse>>
}