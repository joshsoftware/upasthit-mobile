package com.upasthit.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.credentials.Credential
import com.upasthit.data.model.api.response.SyncUpApiResponse
import com.upasthit.data.model.local.db.tables.School
import com.upasthit.data.model.local.db.tables.Staff
import com.upasthit.data.model.local.db.tables.Standard
import com.upasthit.data.network.ErrorUtils
import com.upasthit.ui.base.BaseViewModel
import com.upasthit.util.DebugLog
import retrofit2.Response


open class LoginViewModel : BaseViewModel() {

    private val mSyncDataResponse = MutableLiveData<SyncUpApiResponse>()

    fun getSchoolDetails(mobileNumber: String, pin: String) {
        setProgress(true)
        mDisposable = mNetworkClient.getSynData(mobileNumber, pin, this)!!
    }

    fun getSchoolDetailsResponse(): LiveData<SyncUpApiResponse> {
        return mSyncDataResponse
    }

    //Handle login response
    fun handleSyncDataResponse(response: Response<SyncUpApiResponse>?) {
        when {
            response!!.isSuccessful -> {
                mSyncDataResponse.postValue(response.body())

                // add response to realm database
                val realm = mDatabaseRealm.realmInstance
                realm.executeTransaction { realm ->
                    realm.copyToRealm(response.body()!!)
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
//                val errorResponse = ErrorUtils.getGenericErrorMessage()
                showMessage("This mobile number is not registered, Please contact your School Admin.")
            }
        }
        setProgress(false)
    }

    fun handleApiErrors(errorMessage: String) {
        showMessage(errorMessage)
        setProgress(false)
    }

    fun getSelectorPhoneNumber(requestCode: Int, resultCode: Int, data: Intent?, RC_HINT: Int): String? {
        if (requestCode == RC_HINT && resultCode == Activity.RESULT_OK) {
            val credential = data!!.getParcelableExtra<Credential>(Credential.EXTRA_KEY)
            return credential.id.substring(3)
        }
        return null
    }
}