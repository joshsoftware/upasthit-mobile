package com.upasthit.data.network

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


}