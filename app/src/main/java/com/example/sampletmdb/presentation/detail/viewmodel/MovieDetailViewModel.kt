package com.example.sampletmdb.presentation.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sampletmdb.domain.detail.interactor.GetMovieDetail
import com.example.sampletmdb.domain.detail.model.MovieDetail
import com.example.sampletmdb.presentation.detail.model.MovieDetailPageModel
import com.example.sampletmdb.presentation.detail.model.MovieDetailPageState
import com.example.sampletmdb.utils.PageState

class MovieDetailViewModel(
    private val getMovieDetail: GetMovieDetail
) : ViewModel() {

    private val _liveData = MutableLiveData<PageState<MovieDetailPageState>>()
    val liveData: LiveData<PageState<MovieDetailPageState>>
        get() = _liveData

    private var movieId = -1
    private var movie = MovieDetailPageModel.empty()

    override fun onCleared() {
        getMovieDetail.cancel()
        super.onCleared()
    }

    fun initData(movieId: Int) {
        this.movieId = movieId
        loadMovieDetail(movieId)
    }

    private fun loadMovieDetail(movieId: Int) {
        fun onSuccess(movieDetail: MovieDetail) {
            this.movie = MovieDetailPageModel.from(movieDetail)

            _liveData.value = PageState.Render(
                MovieDetailPageState.Result(
                    this.movie
                )
            )
        }

        fun onError(error: Throwable) {
            _liveData.value = PageState.Render(
                MovieDetailPageState.Error(
                    error.message.orEmpty()
                )
            )
        }

        _liveData.value = PageState.Loading
        getMovieDetail.cancel()
        getMovieDetail(movieId, ::onSuccess, ::onError)
    }

    fun reloadMovieDetail() {
        if (movieId != -1) {
            _liveData.value = PageState.Render(
                MovieDetailPageState.Result(
                    MovieDetailPageModel.empty()
                )
            )
            loadMovieDetail(movieId)
        }
    }
}