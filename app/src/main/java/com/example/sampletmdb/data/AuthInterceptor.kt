package com.example.sampletmdb.data

import com.example.sampletmdb.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    companion object {
        const val QUERY_PARAM_AUTH = "api_key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request()
                .newBuilder()
                .url(
                    chain.request()
                        .url()
                        .newBuilder()
                        .addQueryParameter(QUERY_PARAM_AUTH, BuildConfig.API_KEY)
                        .build()
                )
                .build()
        )
    }
}