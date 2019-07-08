package com.upasthit.data.network


import com.upasthit.BuildConfig
import com.upasthit.util.ApplicationConstant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitApiClient {

    var mNetworkServices: NetworkServices? = null

    private var httpClient: OkHttpClient.Builder? = null

    /**
     * To build retrofit object
     */
    fun getRetrofitClient() {
        mNetworkServices = retrofit().create(NetworkServices::class.java)
    }

    internal fun retrofit(): Retrofit {
        httpClient = OkHttpClient.Builder().apply {
            addInterceptor(HeaderInterceptor())
            addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                level = HttpLoggingInterceptor.Level.BODY

            })
            readTimeout(ApplicationConstant.TIMEOUT_READ.toLong(), TimeUnit.MINUTES)
            connectTimeout(ApplicationConstant.TIMEOUT_CONNECTION.toLong(), TimeUnit.MINUTES)
            writeTimeout(ApplicationConstant.TIMEOUT_CONNECTION.toLong(), TimeUnit.MINUTES)
        }

        return Retrofit.Builder()
                .baseUrl(ApplicationConstant.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient!!.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    /**
     * To connect network service
     */
    fun getNetworkServices(): NetworkServices {
        return mNetworkServices as NetworkServices
    }

}