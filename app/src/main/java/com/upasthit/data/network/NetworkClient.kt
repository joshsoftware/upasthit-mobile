package com.upasthit.data.network

import com.upasthit.data.model.api.request.CreateAttendanceRequest
import com.upasthit.ui.absentstudent.AbsentStudentViewModel
import com.upasthit.ui.login.LoginViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class NetworkClient constructor(networkServices: NetworkServices?) {

    var mNetworkServices: NetworkServices? = null
    val internetError: String = "Oops, something went wrong. Please try again after some time."

    init {
        this.mNetworkServices = networkServices
    }

    private fun getFailureMessage(t: Throwable?): String {
        return if (t is IOException) {
            internetError
        } else {
            t?.message.toString()
        }
    }

    /**
     * To call api for getting school data
     */

    fun getSynData(mobileNo: String, mViewModel: LoginViewModel): Disposable? {
        return mNetworkServices?.syncData(mobileNo)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.subscribe({ t ->
                    mViewModel.handleSyncDataResponse(t)
                }, { t ->
                    mViewModel.handleApiErrors(getFailureMessage(t))
                })
    }

    /**
     * To call api for mark attendance
     */
    fun createAttendance(mCreateDeleteInterestRequest: CreateAttendanceRequest, mViewModel: AbsentStudentViewModel): Disposable? {
        return mNetworkServices?.createAttendance(mCreateDeleteInterestRequest)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.subscribe({ t ->
                    mViewModel.handleAttendanceResponse(t)
                }, { t ->
                    mViewModel.handleApiErrors(getFailureMessage(t))
                })
    }

}