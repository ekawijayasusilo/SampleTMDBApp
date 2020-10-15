package com.example.sampletmdb.utils

import com.example.sampletmdb.data.ErrorResponse
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.Response


fun <T, U> Single<Response<T>>.unfold(
    gson: Gson, mapResponse: T.() -> U
): Single<U> = this.map { response ->
    if (response.isSuccessful) {
        response.body()!!.mapResponse()
    } else {
        val error = gson.fromJson(
            response.errorBody()?.string(),
            ErrorResponse::class.java
        )
        throw Exception("${error.code}-${error.message}")
    }
}

fun CompositeDisposable.launch(block: () -> Disposable) = add(block())