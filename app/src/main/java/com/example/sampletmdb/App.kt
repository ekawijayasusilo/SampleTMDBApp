package com.example.sampletmdb

import android.app.Application
import com.example.sampletmdb.di.dataModule
import com.example.sampletmdb.di.domainModule
import com.example.sampletmdb.di.networkModule
import com.example.sampletmdb.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(networkModule, dataModule, domainModule, presentationModule)
        }
    }

}