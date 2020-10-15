package com.example.sampletmdb.data.detail.model

import com.google.gson.annotations.SerializedName

class LanguageResponse(
    @SerializedName("iso_639_1") val id: String,
    @SerializedName("name") val name: String
)