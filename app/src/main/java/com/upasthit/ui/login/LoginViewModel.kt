package com.upasthit.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.upasthit.data.model.local.db.tables.SyacUpApiResponse
import com.upasthit.data.network.ErrorUtils
import com.upasthit.ui.base.BaseViewModel
import retrofit2.Response
import android.R.attr.resource


class LoginViewModel : BaseViewModel() {

    private val mSyncDataResponse = MutableLiveData<SyacUpApiResponse>()

    init {
        getCountries()
    }

    fun getCountries() {
        setProgress(true)
        mDisposable = mNetworkClient.getSynData("7798805221", this)!!
    }

    fun getCountriesResponse(): LiveData<SyacUpApiResponse> {
        return mSyncDataResponse
    }

    //Handle login response
    fun handleSyncDataResponse(response: Response<SyacUpApiResponse>?) {
        when {
            response!!.isSuccessful -> {
                mSyncDataResponse.postValue(response.body())

                // add response to realm database
                val realm = mDatabaseRealm.realmInstance
                realm.beginTransaction()
                realm.copyToRealmOrUpdate(response.body())
                realm.commitTransaction()
                realm.close()
            }

            response.code() == 401 -> {
                showMessage(ErrorUtils.getErrorMessage(response))
                setHandleAuthorizationFailed(true)
            }

            else -> {
                val errorResponse = ErrorUtils.getGenericErrorMessage()
                showMessage(errorResponse)
            }
        }
        setProgress(false)
    }

    fun handleApiErrors(errorMessage: String) {
        showMessage(errorMessage)
        setProgress(false)
    }

}