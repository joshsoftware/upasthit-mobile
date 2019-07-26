package com.upasthit.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.upasthit.data.model.local.db.tables.School
import com.upasthit.data.model.local.db.tables.Staff
import com.upasthit.data.model.local.db.tables.Standard
import com.upasthit.data.model.local.db.tables.SyacUpApiResponse
import com.upasthit.data.network.ErrorUtils
import com.upasthit.ui.base.BaseViewModel
import com.upasthit.util.DebugLog
import retrofit2.Response


class LoginViewModel : BaseViewModel() {

    private val mSyncDataResponse = MutableLiveData<SyacUpApiResponse>()

    init {
//        getSchoolDetails()
    }

    fun getSchoolDetails(mobileNumber: String) {
        setProgress(true)
        mDisposable = mNetworkClient.getSynData(mobileNumber, this)!!
    }

    fun getSchoolDetailsResponse(): LiveData<SyacUpApiResponse> {
        return mSyncDataResponse
    }

    //Handle login response
    fun handleSyncDataResponse(response: Response<SyacUpApiResponse>?) {
        when {
            response!!.isSuccessful -> {
                mSyncDataResponse.postValue(response.body())

                // add response to realm database
                val realm = mDatabaseRealm.realmInstance
                realm.executeTransaction { realm ->
                    realm.copyToRealm(response.body())
                }

                val notesCount = realm.where(School::class.java).findAll().count()
                val notesCount2 = realm.where(Standard::class.java).findAll().count()
                val notesCount3 = realm.where(Staff::class.java).findAll().count()

                DebugLog.e("School count     $notesCount")
                DebugLog.e("Standard count    $notesCount2")
                DebugLog.e("Staff count      $notesCount3")
                DebugLog.e("Staff standard count " + realm.where(Staff::class.java).findFirst()?.standard_ids?.count())
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