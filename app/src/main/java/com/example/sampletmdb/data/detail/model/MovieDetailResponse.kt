package com.example.sampletmdb.data.detail.model

import com.example.sampletmdb.domain.detail.model.MovieDetail
import com.google.gson.annotations.SerializedName

class MovieDetailResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("genres") val genres: List<GenreResponse>,
    @SerializedName("adult") val isAdult: Boolean,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("video") val hasVideo: Boolean,
    @SerializedName("overview") val overview: String,
    @SerializedName("tagline") val tagline: String?,
    @SerializedName("homepage") val homepage: String?,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("budget") val budget: Int,
    @SerializedName("revenue") val revenue: Int,
    @SerializedName("production_companies") val productionCompanies: List<CompanyResponse>,
    @SerializedName("production_countries") val productionCountries: List<CountryResponse>,
    @SerializedName("spoken_languages") val spokenLanguages: List<LanguageResponse>,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("vote_average") val voteAverage: Double
) {
    fun toMovieDetail() = MovieDetail(
        id,
        title,
        posterPath,
        backdropPath,
        popularity,
        overview,
        genres.map { it.name },
        originalLanguage,
        runtime
    )
}