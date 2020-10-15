package com.example.sampletmdb.data

import com.google.gson.annotations.SerializedName

class ErrorResponse(
    @SerializedName("status_code") val code: Int,
    @SerializedName("status_message") val message: String
)