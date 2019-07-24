package com.upasthit.data.network

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
        if (t is IOException) {
            return internetError
        } else {
            return t?.message.toString()
        }
    }

    /**
     * To call api for getting all countries
     */

    fun getSynData(mobileNo: String,
                     mViewModel: LoginViewModel): Disposable? {
        return mNetworkServices?.syncData(mobileNo)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.subscribe({ t ->
                    mViewModel.handleSyncDataResponse(t)
                }, { t ->
                    mViewModel.handleApiErrors(getFailureMessage(t))
                })
    }


}