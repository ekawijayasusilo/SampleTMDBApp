package com.example.sampletmdb.utils

import com.example.sampletmdb.domain.SchedulerProvider
import io.reactivex.schedulers.Schedulers

class TrampolineSchedulerProvider : SchedulerProvider {
    override fun executionThread() = Schedulers.trampoline()

    override fun postExecutionThread() = Schedulers.trampoline()
}

