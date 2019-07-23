package com.upasthit.util

import com.upasthit.data.network.Environment

object ApplicationConstant {

    //Flag for debugging log mode
    var isDebuggable = true

    //Api url component
    val SERVER_URL = Environment.STAGING.baseUrl

    //Connection timeout
    val TIMEOUT_CONNECTION = 5
    val TIMEOUT_READ = 5

    //MixPanel Api Token
    val MIXPANEL_API_TOKEN = "05f195a87a8196c14768940d4a8b7951"

    //Http request header content
    const val ACCEPT_TYPE = "application/json"
    const val CONTENT_TYPE = "application/json"
    val LOCALE = "mr-IN"
    val COUNTRY_CODE = "+91"

    //Fragment transactions
    const val ADD_FRAGMENT = 1
    const val REPLACE_FRAGMENT = 2
    const val REMOVE_FRAGMENT = 3

    //Bundles
    const val ABSENT_STUDENT_DATA = "absent_student_data"
    const val IS_FROM_NAVIGATION_DRAWER = "isFromNavigationDrawer"

}