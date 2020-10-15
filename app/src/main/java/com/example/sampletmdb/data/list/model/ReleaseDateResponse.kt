package com.example.sampletmdb.data.list.model

import com.google.gson.annotations.SerializedName

class ReleaseDateResponse(
    @SerializedName("minimum") val earliestDate: String,
    @SerializedName("maximum") val latestDate: String
)