package com.example.sampletmdb.domain

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class IOSchedulerProvider : SchedulerProvider {
    override fun executionThread() = Schedulers.io()

    override fun postExecutionThread() = AndroidSchedulers.mainThread()
}