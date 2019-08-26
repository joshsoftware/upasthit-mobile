package com.upasthit.data.network

import com.upasthit.data.model.api.request.CreateAttendanceRequest
import com.upasthit.data.model.api.response.CreateAttendanceResponse
import com.upasthit.data.model.api.response.SyncUpApiResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface NetworkServices {


    @GET("v1/staffs/sync")
    fun syncData(@Header("X-User-Mob-Num") mobileNumber: String,
                 @Header("X-User-Pin") pin: String): Observable<Response<SyncUpApiResponse>>

    @POST("v1/attendances")
    fun createAttendance(@Header("X-User-Mob-Num") mobileNumber: String,
                         @Header("X-User-Pin") pin: String,
                         @Body request: CreateAttendanceRequest): Observable<Response<CreateAttendanceResponse>>
}