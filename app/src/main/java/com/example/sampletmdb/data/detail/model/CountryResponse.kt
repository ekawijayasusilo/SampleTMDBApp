package com.example.sampletmdb.data.detail.model

import com.google.gson.annotations.SerializedName

class CountryResponse(
    @SerializedName("iso_3166_1") val id: String,
    @SerializedName("name") val name: String
)