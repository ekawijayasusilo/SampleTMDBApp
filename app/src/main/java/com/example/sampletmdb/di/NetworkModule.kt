package com.example.sampletmdb.di

import com.example.sampletmdb.BuildConfig
import com.example.sampletmdb.data.AuthInterceptor
import com.example.sampletmdb.data.LocalizationInterceptor
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val LOGGING_INTERCEPTOR = "LoggingInterceptor"
const val AUTH_INTERCEPTOR = "AuthInterceptor"
const val LOCALIZATION_INTERCEPTOR = "LocalizationInterceptor"

val networkModule = module {
    single<Interceptor>(named(LOGGING_INTERCEPTOR)) {
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BASIC)
    }

    single<Interceptor>(named(AUTH_INTERCEPTOR)) {
        AuthInterceptor()
    }

    single<Interceptor>(named(LOCALIZATION_INTERCEPTOR)) {
        LocalizationInterceptor()
    }

    single<CallAdapter.Factory> {
        RxJava2CallAdapterFactory.create()
    }

    single<Converter.Factory> {
        GsonConverterFactory.create()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get(named(LOGGING_INTERCEPTOR)))
            .addInterceptor(get(named(AUTH_INTERCEPTOR)))
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addCallAdapterFactory(get())
            .addConverterFactory(get())
            .build()
    }

    single {
        Gson()
    }
}