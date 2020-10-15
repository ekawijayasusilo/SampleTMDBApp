package com.example.sampletmdb.data.detail.model

import com.google.gson.annotations.SerializedName

class GenreResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)