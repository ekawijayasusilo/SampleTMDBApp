package com.example.sampletmdb.data

import com.example.sampletmdb.data.list.model.NowPlayingMoviesResponse
import com.example.sampletmdb.data.detail.model.MovieDetailResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {
    companion object {
        const val PATH_MOVIE_ID = "movie_id"
        const val QUERY_PARAM_PAGE = "page"
        const val ENDPOINT_MOVIES = "movie/now_playing"
        const val ENDPOINT_MOVIE_DETAIL = "movie/{${PATH_MOVIE_ID}}"
    }

    @GET(ENDPOINT_MOVIES)
    fun getNowPlayingMovies(
        @Query(QUERY_PARAM_PAGE) page: Int
    ): Single<Response<NowPlayingMoviesResponse>>

    @GET(ENDPOINT_MOVIE_DETAIL)
    fun getMovieDetail(
        @Path(PATH_MOVIE_ID) movieId: Int
    ): Single<Response<MovieDetailResponse>>
}