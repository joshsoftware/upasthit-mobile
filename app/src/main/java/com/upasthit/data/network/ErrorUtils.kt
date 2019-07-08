package com.upasthit.data.network

import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Response

import java.io.IOException

object ErrorUtils {

    fun parseError(response: Response<*>/*,context: Context*/): APIError {
        val converter =
                RetrofitApiClient.retrofit().responseBodyConverter<APIError>(APIError::class.java, arrayOfNulls(0))

        val error: APIError
        try {
            error = converter.convert(response.errorBody()!!)
        } catch (e: IOException) {
            return APIError()
        }

        return error
    }

    /*
    * This method is used to get the message field from 401 error body.
    * */
    fun <T> getErrorMessage(response: Response<T>?): String {
        val error = response?.errorBody()!!.string()
        val errorMsg: JsonObject =
                Gson().fromJson(error, JsonObject::class.java)
        if (errorMsg.has("message")) {
            return errorMsg.get("message").asString
        } else if (errorMsg.has("error")) {
            return errorMsg.get("error").asString
        } else {
            return response.message()
        }
    }

    fun getGenericErrorMessage(): String {
        return "Oops, something went wrong. Please try again after some time."
    }

}