package com.example.sampletmdb.domain.list.model

import java.util.*

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val popularity: Double,
    val releaseDate: Date
) {
    companion object {
        fun empty() = Movie(0, "", null, 0.0, Date())
    }
}