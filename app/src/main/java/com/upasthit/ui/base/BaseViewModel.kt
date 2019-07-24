package com.upasthit.ui.base

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.upasthit.ApplicationClass
import com.upasthit.data.model.local.db.DatabaseRealm
import com.upasthit.data.network.NetworkClient
import com.upasthit.data.network.RetrofitApiClient
import io.reactivex.disposables.Disposable


abstract class BaseViewModel : ViewModel(), Observable {

    @Transient
    private var mCallbacks: PropertyChangeRegistry? = null

    protected var mNetworkClient: NetworkClient = NetworkClient(RetrofitApiClient.getNetworkServices())

    protected var mDatabaseRealm: DatabaseRealm = ApplicationClass.mDataBaseRealm

    private val showProcess = MutableLiveData<Boolean>()

    protected lateinit var mDisposable: Disposable

    protected val messageData = MutableLiveData<String>()

    protected val authorizationFailedData = MutableLiveData<Boolean>()

    private val mIsLoading = ObservableBoolean(false)

    fun getMessage(): LiveData<String> {
        return messageData
    }

    fun showMessage(message: String) {
        messageData.postValue(message)
    }

    fun getAuthorizationFailedListener(): LiveData<Boolean> {
        return authorizationFailedData;
    }

    override fun onCleared() {
        if (::mDisposable.isInitialized)
            mDisposable.dispose()
        super.onCleared()
    }

//    fun getLoggedInUser(): UserData? {
//        if (PreferencesHelper.getUser() != null) {
//            val jsonLoggedInUser = PreferencesHelper.getUser()
//            return jsonLoggedInUser as UserData
//        } else {
//            return null
//        }
//    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                mCallbacks = PropertyChangeRegistry()
            }
        }
        mCallbacks!!.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.remove(callback)
    }


    fun notifyPropertyChanged(fieldId: Int) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.notifyCallbacks(this, fieldId, null)
    }

    fun getProgress(): LiveData<Boolean> {
        return showProcess
    }


    protected fun setProgress(boolean: Boolean) {
        showProcess.value = (boolean)
    }

    protected fun setHandleAuthorizationFailed(boolean: Boolean) {
        if (boolean) {
            authorizationFailedData.value = (boolean)
        }
    }

}