package com.upasthit.data.model.local.pref

import android.content.Context
import com.google.gson.Gson
import com.upasthit.ApplicationClass

object AppPreferenceStorage {
    private const val mAppPref = "mAppPref"

    private const val IS_APP_OPEN_FIRST_TIME = "is_app_open_first_time"
    private const val LOGGED_IN = "logged_in"
    private const val AUTH_TOKEN = "auth_token"
    private const val USER_INFO = "user_info"




    fun saveIsAppOpenFirstTime(status: Boolean) {
        val hxPrefs = ApplicationClass.mAppContext.getSharedPreferences(mAppPref, Context.MODE_PRIVATE)
        val editor = hxPrefs.edit()
        editor.putBoolean(IS_APP_OPEN_FIRST_TIME, status)

        isAppOpenFirstTime = status
        editor.apply()
    }

    var isAppOpenFirstTime: Boolean? = false
        get() {
            val hxPrefs = ApplicationClass.mAppContext.getSharedPreferences(mAppPref, Context.MODE_PRIVATE)
            isAppOpenFirstTime = hxPrefs.getBoolean(IS_APP_OPEN_FIRST_TIME, false)
            return field
        }
        private set

    fun saveUserLoggedIn(status: Boolean) {
        val hxPrefs = ApplicationClass.mAppContext.getSharedPreferences(mAppPref, Context.MODE_PRIVATE)
        val editor = hxPrefs.edit()
        editor.putBoolean(LOGGED_IN, status)

        userLoggedIn = status
        editor.apply()
    }

    var userLoggedIn: Boolean? = false
        get() {
            val hxPrefs = ApplicationClass.mAppContext.getSharedPreferences(mAppPref, Context.MODE_PRIVATE)
            userLoggedIn = hxPrefs.getBoolean(LOGGED_IN, false)
            return field
        }
        private set

    /**
     * to set Auth Token
     */
    fun saveAuthToken(token: String) {
        val hxPrefs = ApplicationClass.mAppContext.getSharedPreferences(mAppPref, Context.MODE_PRIVATE)
        val editor = hxPrefs.edit()
        editor.putString(AUTH_TOKEN, token)
        ApplicationClass.authToken = token
        editor.apply()
    }

    /**
     * To get Auth Token
     */
    var authToken: String? = ""
        get() {
            val hxPrefs = ApplicationClass.mAppContext.getSharedPreferences(mAppPref, Context.MODE_PRIVATE)
            authToken = hxPrefs.getString(AUTH_TOKEN, "")
            return field
        }
        private set


//    /**
//     * to set user info
//     */
//    fun saveUserInfo(user: User) {
//
//        val hxPrefs = ApplicationClass.mAppContext.getSharedPreferences(mAppPref, Context.MODE_PRIVATE)
//        val editor = hxPrefs.edit()
//        if (user != null) {
//            editor.putString(USER_INFO, Gson().toJson(user))
//        } else {
//            editor.putString(USER_INFO, Gson().toJson(null))
//        }
//        editor.apply()
//    }
//
//    /**
//     * to get user info
//     */
//    var userInfo: User? = null
//        get() {
//            val hxPrefs = ApplicationClass.mAppContext.getSharedPreferences(mAppPref, Context.MODE_PRIVATE)
//            val userData = hxPrefs.getString(USER_INFO, "")
//            if (userData != null) {
//                userInfo = Gson().fromJson<User>(userData, User::class.java)
//            } else {
//                userInfo = null
//            }
//            return field
//        }

    fun removePreference() {
        val hxPrefs = ApplicationClass.mAppContext.getSharedPreferences(mAppPref, Context.MODE_PRIVATE)
        val editor = hxPrefs.edit()

        editor.clear()
        editor.apply()
    }

}