package com.example.sampletmdb.domain.detail.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val backdropPath: String?,
    val popularity: Double,
    val overview: String,
    val genres: List<String>,
    val language: String,
    val runtime: Int?
) {
    companion object {
        fun empty() = MovieDetail(0, "", null, null, 0.0, "", emptyList(), "", null)
    }
}