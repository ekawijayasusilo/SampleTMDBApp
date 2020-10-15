package com.example.sampletmdb.data

import com.example.sampletmdb.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class LocalizationInterceptor : Interceptor {
    companion object {
        const val QUERY_PARAM_LANGUAGE = "language"
        const val QUERY_PARAM_REGION = "region"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request()
                .newBuilder()
                .url(
                    chain.request()
                        .url()
                        .newBuilder()
                        .addQueryParameter(QUERY_PARAM_LANGUAGE, BuildConfig.LANGUAGE)
                        .addQueryParameter(QUERY_PARAM_REGION, BuildConfig.REGION)
                        .build()
                )
                .build()
        )
    }
}