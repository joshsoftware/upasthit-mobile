package com.upasthit

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.upasthit.data.model.local.db.DatabaseRealm
import com.upasthit.data.network.RetrofitApiClient
import com.upasthit.data.model.local.pref.AppPreferenceStorage

class ApplicationClass : Application() {
    companion object {

        var apiKey = ""
        var authToken = ""
        val STAGE = 0
        val PRODUCTION = 1

        val APP_MODE = STAGE

        @SuppressLint("StaticFieldLeak")
        lateinit var mAppContext: Context
        lateinit var mDataBaseRealm: DatabaseRealm

    }

    override fun onCreate() {
        super.onCreate()
        mAppContext = applicationContext
//        ApplicationClass.apiKey = AppPreferenceStorage.getApiKey()
        ApplicationClass.authToken = AppPreferenceStorage.authToken!!


        /**
         * Retrofit
         */
        RetrofitApiClient.getRetrofitClient()

        /**
         * Fabrics initialisation
         */
        if (ApplicationClass.APP_MODE == ApplicationClass.PRODUCTION) {
//            Fabric.with(this, Crashlytics())
        }

        /**
         * Realm initialization
         */
        initRealm()

    }

    private fun initRealm() {
        mDataBaseRealm = DatabaseRealm()
        mDataBaseRealm.setup(this)
    }
}