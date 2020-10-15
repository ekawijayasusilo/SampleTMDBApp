package com.example.sampletmdb.data.detail.model

import com.google.gson.annotations.SerializedName

class CompanyResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("logo_path") val logoPath: String?,
    @SerializedName("origin_country") val originCountry: String
)