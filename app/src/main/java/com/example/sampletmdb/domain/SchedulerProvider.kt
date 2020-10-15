package com.example.sampletmdb.domain

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun executionThread(): Scheduler
    fun postExecutionThread(): Scheduler
}