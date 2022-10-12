package com.plutoisnotaplanet.mortyapp.application.data.rest.interceptors

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.plutoisnotaplanet.mortyapp.application.data.rest.exception.ApiException
import com.plutoisnotaplanet.mortyapp.application.data.rest.exception.ApiException.HasNotInternetConnection
import com.plutoisnotaplanet.mortyapp.application.utils.ConnectivityObserver
import com.plutoisnotaplanet.mortyapp.application.utils.NetworkConnectivityObserver
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class ResponseInterceptor(
    private val connectivityObserver: ConnectivityObserver,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!connectivityObserver.hasConnection) {
            throw HasNotInternetConnection()
        }
        val request = chain.request()
        val response = chain.proceed(request)
        val bodyString = response.body!!.string()

        checkCommonErrors(response)

        if (response.isSuccessful) {
            return copyResponse(response, bodyString)
        }
        throw ApiException.BadRequest()
    }

    private fun copyResponse(response: Response, body: String): Response {
        val builder = response.newBuilder()
        response.body.use {
            it?.let {
                builder.body(body.toResponseBody(it.contentType()))
            }
        }
        return builder.build()
    }

    private fun checkCommonErrors(response: Response) {
        if (!response.isSuccessful) {
            when (response.code) {
                400 -> throw ApiException.BadRequest()
                401 -> throw ApiException.UnauthorizedException()
                429 -> throw ApiException.TooManyRequests()
                500 -> throw ApiException.InternalServerError()
            }
        }
    }

}