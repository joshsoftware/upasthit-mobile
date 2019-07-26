package com.upasthit.ui.absentstudent

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

    private val mCreateAttendanceResponse = MutableLiveData<CreateAttendanceResponse>()

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
}