package com.upasthit.ui.absentstudent

import android.telephony.SmsManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.upasthit.BR
import com.upasthit.data.model.api.CreateAttendanceResponse
import com.upasthit.data.model.api.request.CreateAttendanceRequest
import com.upasthit.data.model.local.db.tables.Student
import com.upasthit.data.network.ErrorUtils
import com.upasthit.ui.base.BaseViewModel
import retrofit2.Response

class AbsentStudentViewModel : BaseViewModel() {

    var mDataList: MutableList<Student> = ArrayList()
    val sentSmsLabel = "Attendance marked successfully"

    private val mCreateAttendanceResponse = MutableLiveData<CreateAttendanceResponse>()
    private val sendMessageResponse = MutableLiveData<String>()

    fun setAbsentStudentList(absentStudentList: ArrayList<Student>) {
        mDataList.clear()
        mDataList.addAll(absentStudentList)
        notifyPropertyChanged(BR._all)
    }

    fun createAttendanceRequest(mCreateDeleteInterestRequest: CreateAttendanceRequest) {
        setProgress(true)
        mDisposable = mNetworkClient.createAttendance(mCreateDeleteInterestRequest, this)!!
    }

    fun getAttendanceResponse(): LiveData<CreateAttendanceResponse> {
        return mCreateAttendanceResponse
    }

    fun handleAttendanceResponse(response: Response<CreateAttendanceResponse>?) {
        when {
            response!!.isSuccessful -> {
                mCreateAttendanceResponse.postValue(response.body())
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

    fun sendMessage(strMobileNo: String, strMessage: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(strMobileNo, null, strMessage, null, null)
            showMessage(sentSmsLabel)
            sendMessageResponse.postValue(sentSmsLabel)
        } catch (ex: Exception) {
            showMessage("Unable to update attendance. Try again later")
        }
    }

    fun getSendMessageResponse(): LiveData<String> {
        return sendMessageResponse
    }
}