package com.example.optisolassesment.di

import com.example.optisolassesment.network.ApiService
import com.example.optisolassesment.utils.Constant
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val retrofitModule = module {
    single { getNetworkApi(getOkKttp()) }
}

fun getOkKttp(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS) // connect timeout
        .readTimeout(60, TimeUnit.SECONDS).build()
}


/**
 * Used to get the instance of Network Api
 */
private fun getNetworkApi(okHttp: OkHttpClient): ApiService {
    return Retrofit.Builder().baseUrl(Constant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp).build().create(ApiService::class.java)
}