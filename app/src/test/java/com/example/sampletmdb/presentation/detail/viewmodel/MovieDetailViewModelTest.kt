package com.example.sampletmdb.presentation.detail.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.sampletmdb.domain.detail.interactor.GetMovieDetail
import com.example.sampletmdb.domain.detail.model.MovieDetail
import com.example.sampletmdb.presentation.detail.model.MovieDetailPageModel
import com.example.sampletmdb.presentation.detail.model.MovieDetailPageState
import com.example.sampletmdb.utils.PageState
import com.example.sampletmdb.utils.RxImmediateSchedulerRule
import com.nhaarman.mockitokotlin2.*
import org.junit.*
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MovieDetailViewModelTest {

    companion object {
        @JvmStatic
        private lateinit var viewModel: MovieDetailViewModel

        @JvmStatic
        private lateinit var getMovieDetail: GetMovieDetail

        @JvmStatic
        private lateinit var observer: Observer<PageState<MovieDetailPageState>>

        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()

        @ClassRule
        @JvmField
        var rule = InstantTaskExecutorRule()

        @BeforeClass
        @JvmStatic
        fun setUp() {
            getMovieDetail = mock()
            observer = mock()
            viewModel = MovieDetailViewModel(getMovieDetail)
        }
    }

    @Before
    fun prepare() {
        reset(getMovieDetail, observer)

        viewModel.liveData.observeForever(observer)
    }

    @After
    fun deconstruct() {
        viewModel.liveData.removeObserver(observer)
    }

    @Test
    fun `1(initData) given success result, when initData is called, then should post successful render state`() {
        // given
        val movieId = 10
        val onSuccess = argumentCaptor<(MovieDetail) -> Unit>()
        val response = MovieDetail.empty()
        val expectedState = PageState.Render(
            MovieDetailPageState.Result(
                MovieDetailPageModel.from(response)
            )
        )
        whenever(
            getMovieDetail.invoke(any(), onSuccess.capture(), any())
        ).then {
            onSuccess.firstValue.invoke(response)
        }

        // when
        viewModel.initData(movieId)

        // then
        verify(observer).onChanged(PageState.Loading)
        verify(getMovieDetail).cancel()
        verify(getMovieDetail).invoke(same(movieId), any(), any())
        verify(observer).onChanged(expectedState)
        verifyNoMoreInteractions(getMovieDetail)
    }

    @Test
    fun `2(initData) given error result, when initData is called, then should post erroneous render state`() {
        // given
        val movieId = 10
        val onError = argumentCaptor<(Throwable) -> Unit>()
        val response = Exception("message")
        val expectedState = PageState.Render(
            MovieDetailPageState.Error(
                response.message.orEmpty()
            )
        )
        whenever(
            getMovieDetail.invoke(any(), any(), onError.capture())
        ).then {
            onError.firstValue.invoke(response)
        }

        // when
        viewModel.initData(movieId)

        // then
        verify(observer).onChanged(PageState.Loading)
        verify(getMovieDetail).cancel()
        verify(getMovieDetail).invoke(same(movieId), any(), any())
        verify(observer).onChanged(expectedState)
        verifyNoMoreInteractions(getMovieDetail)
    }

    @Test
    fun `3(reloadMovieDetail) given success result, when reloadMovieDetail is called, then should post successful render state`() {
        // given
        val movieId = 10
        val onSuccess = argumentCaptor<(MovieDetail) -> Unit>()
        val response = MovieDetail.empty()
        val preliminaryState = PageState.Render(
            MovieDetailPageState.Result(
                MovieDetailPageModel.empty()
            )
        )
        val expectedState = PageState.Render(
            MovieDetailPageState.Result(
                MovieDetailPageModel.from(response)
            )
        )
        whenever(
            getMovieDetail.invoke(any(), onSuccess.capture(), any())
        ).then {
            onSuccess.firstValue.invoke(response)
        }

        // when
        viewModel.reloadMovieDetail()

        // then
        verify(observer).onChanged(preliminaryState)
        verify(observer).onChanged(PageState.Loading)
        verify(getMovieDetail).cancel()
        verify(getMovieDetail).invoke(same(movieId), any(), any())
        verify(observer).onChanged(expectedState)
        verifyNoMoreInteractions(getMovieDetail)
    }

    @Test
    fun `4(reloadMovieDetail) given error result, when reloadMovieDetail is called, then should post erroneous render state`() {
        // given
        val movieId = 10
        val onError = argumentCaptor<(Throwable) -> Unit>()
        val response = Exception("message")
        val preliminaryState = PageState.Render(
            MovieDetailPageState.Result(
                MovieDetailPageModel.empty()
            )
        )
        val expectedState = PageState.Render(
            MovieDetailPageState.Error(
                response.message.orEmpty()
            )
        )
        whenever(
            getMovieDetail.invoke(any(), any(), onError.capture())
        ).then {
            onError.firstValue.invoke(response)
        }

        // when
        viewModel.reloadMovieDetail()

        // then
        verify(observer).onChanged(preliminaryState)
        verify(observer).onChanged(PageState.Loading)
        verify(getMovieDetail).cancel()
        verify(getMovieDetail).invoke(same(movieId), any(), any())
        verify(observer).onChanged(expectedState)
        verifyNoMoreInteractions(getMovieDetail)
    }
}