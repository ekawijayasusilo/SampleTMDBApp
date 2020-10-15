package com.example.sampletmdb.domain.detail.interactor

import com.example.sampletmdb.domain.SchedulerProvider
import com.example.sampletmdb.domain.detail.model.MovieDetail
import com.example.sampletmdb.domain.detail.repository.MovieDetailRepository
import com.example.sampletmdb.utils.TrampolineSchedulerProvider
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

class GetMovieDetailTest {

    companion object {
        @JvmStatic
        private lateinit var getMovieDetail: GetMovieDetail

        @JvmStatic
        private lateinit var schedulerProvider: SchedulerProvider
        @JvmStatic
        private lateinit var repository: MovieDetailRepository
        @JvmStatic
        private lateinit var onSuccess: (MovieDetail) -> Unit
        @JvmStatic
        private lateinit var onError: (Throwable) -> Unit

        @BeforeClass
        @JvmStatic
        fun setUp() {
            schedulerProvider = TrampolineSchedulerProvider()
            repository = mock()
            onSuccess = mock()
            onError = mock()

            getMovieDetail = GetMovieDetail(schedulerProvider, repository)
        }
    }

    @Before
    fun prepare() {
        reset(repository, onSuccess, onError)
    }

    @Test
    fun `(invoke) given success result, when invoke is called, then should call onSuccess`() {
        // given
        val id = 10
        val expected = MovieDetail.empty()
        whenever(
            repository.getMovieDetail(any())
        ).thenReturn(Single.just(expected))
        whenever(
            onSuccess.invoke(any())
        ).thenReturn(Unit)

        // when
        getMovieDetail.invoke(id, onSuccess, onError)

        // then
        verify(repository).getMovieDetail(same(id))
        verifyNoMoreInteractions(repository)
        verify(onSuccess).invoke(eq(expected))
        verifyZeroInteractions(onError)
    }

    @Test
    fun `(invoke) given error result, when invoke is called, then should call onError`() {
        // given
        val id = 10
        val expected = NullPointerException()
        whenever(
            repository.getMovieDetail(any())
        ).thenReturn(Single.error(expected))
        whenever(
            onError.invoke(any())
        ).thenReturn(Unit)

        // when
        getMovieDetail.invoke(id, onSuccess, onError)

        // then
        verify(repository).getMovieDetail(same(id))
        verifyNoMoreInteractions(repository)
        verify(onError).invoke(eq(expected))
        verifyZeroInteractions(onSuccess)
    }
}