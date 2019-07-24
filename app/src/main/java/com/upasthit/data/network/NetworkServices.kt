package com.upasthit.data.network

import com.upasthit.data.model.local.db.tables.SyacUpApiResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkServices {
    @GET("v1/staffs/sync")
    fun syncData(@Query("mobile_number") mobileNumber: String): Observable<Response<SyacUpApiResponse>>
}