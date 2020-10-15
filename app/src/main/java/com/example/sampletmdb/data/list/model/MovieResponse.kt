package com.example.sampletmdb.data.list.model

import com.example.sampletmdb.domain.list.model.Movie
import com.example.sampletmdb.utils.toDate
import com.google.gson.annotations.SerializedName

class MovieResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("adult") val isAdult: Boolean,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("video") val hasVideo: Boolean,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("vote_average") val voteAverage: Double
) {
    companion object {
        const val RELEASE_DATE_PATTERN = "yyyy-MM-dd"
    }

    fun toMovie() =
        Movie(id, title, posterPath, popularity, releaseDate.toDate(RELEASE_DATE_PATTERN))
}