package com.plutoisnotaplanet.mortyapp.application.data.rest

import androidx.viewbinding.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.plutoisnotaplanet.mortyapp.application.data.rest.interceptors.ResponseInterceptor
import com.plutoisnotaplanet.mortyapp.application.utils.ConnectivityObserver
import com.plutoisnotaplanet.mortyapp.application.utils.NetworkConnectivityObserver
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiFactory {

    fun createOkHttpClient(
        connectivityObserver: ConnectivityObserver
    ): OkHttpClient {
        val httpLoggingInterceptor: HttpLoggingInterceptor = createHttpLoggingInterceptor()
        val responseInterceptor: ResponseInterceptor =
            createResponseInterceptor(connectivityObserver)

        val okHttpClient: OkHttpClient = createOkHttpClient(
            httpLoggingInterceptor,
            responseInterceptor
        )

        return okHttpClient
    }

    fun create(
        baseUrl: String,
        okHttpClient: OkHttpClient
    ): Api {
        val gson: Gson = createGson()

        return createRetrofit(okHttpClient, gson)
            .baseUrl(baseUrl)
            .build()
            .create(Api::class.java)
    }


    private fun createOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        responseInterceptor: ResponseInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .apply {
                addInterceptor(interceptor)
                addInterceptor(responseInterceptor)
            }

        return okHttpClient.build()
    }


    private fun createHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY
        )

    private fun createGson(): Gson = GsonBuilder().setLenient().create()

    private fun createRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit.Builder =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))

    private fun createResponseInterceptor(
        connectivityObserver: ConnectivityObserver,
    ): ResponseInterceptor =
        ResponseInterceptor(connectivityObserver)
}