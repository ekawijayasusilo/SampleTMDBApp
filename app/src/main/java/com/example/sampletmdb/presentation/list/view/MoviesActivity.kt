package com.example.sampletmdb.presentation.list.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.sampletmdb.databinding.ActivityMoviesBinding
import com.example.sampletmdb.databinding.SublayoutErrorBinding
import com.example.sampletmdb.databinding.SublayoutLoadingBinding
import com.example.sampletmdb.presentation.detail.view.MovieDetailActivity
import com.example.sampletmdb.presentation.list.adapter.MoviesAdapter
import com.example.sampletmdb.presentation.list.model.MoviePageModel
import com.example.sampletmdb.presentation.list.model.MoviesPageState
import com.example.sampletmdb.presentation.list.viewmodel.MoviesViewModel
import com.example.sampletmdb.utils.PageState
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesActivity : AppCompatActivity(), MoviesAdapter.MoviesAdapterListener {

    private val viewModel: MoviesViewModel by viewModel()
    private val adapter = MoviesAdapter(this)
    private lateinit var binding: ActivityMoviesBinding
    private lateinit var bindingError: SublayoutErrorBinding
    private lateinit var bindingLoading: SublayoutLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesBinding.inflate(layoutInflater)
        bindingError = SublayoutErrorBinding.bind(binding.root)
        bindingLoading = SublayoutLoadingBinding.bind(binding.root)
        setContentView(binding.root)

        initUI()
        initObserver()
    }

    private fun initUI() {
        binding.recyclerViewMovies.adapter = adapter
        binding.swipeRefresh.setOnRefreshListener { viewModel.loadInitialPage() }
    }

    private fun initObserver() {
        viewModel.liveData.observe(::getLifecycle, ::setupView)
        viewModel.loadInitialPage()
    }

    private fun setupView(state: PageState<MoviesPageState>) {
        when (state) {
            PageState.Loading -> showLoading()
            is PageState.Render -> render(state.renderState)
        }
    }

    private fun showLoading() {
        bindingLoading.cardViewLoading.visibility = View.VISIBLE
        bindingError.groupError.visibility = View.GONE
        binding.linearLayoutLoadNextPage.visibility = View.GONE
    }

    private fun render(renderState: MoviesPageState) {
        when (renderState) {
            MoviesPageState.LoadingNextPage -> showLoadPaging()
            is MoviesPageState.Result -> showResult(
                renderState.movies,
                renderState.isFirstPageResult
            )
            is MoviesPageState.Error -> showError(
                renderState.message,
                renderState.isFirstPageResult
            )
        }
    }

    private fun showLoadPaging() {
        binding.linearLayoutLoadNextPage.visibility = View.VISIBLE
    }

    private fun showResult(movies: List<MoviePageModel>, isFirstPageResult: Boolean) {
        if (isFirstPageResult) {
            showFirstPageResult(movies)
        } else {
            showNextPageResult(movies)
        }
    }

    private fun showFirstPageResult(movies: List<MoviePageModel>) {
        adapter.submitList(movies)
        binding.recyclerViewMovies.visibility = View.VISIBLE
        bindingLoading.cardViewLoading.visibility = View.GONE
        binding.swipeRefresh.isRefreshing = false
    }

    private fun showNextPageResult(movies: List<MoviePageModel>) {
        adapter.submitList(movies)
        binding.linearLayoutLoadNextPage.visibility = View.GONE
    }

    private fun showError(message: String, isFirstPageResult: Boolean) {
        if (isFirstPageResult) {
            showFirstPageError(message)
        } else {
            showNextPageError(message)
        }
    }

    private fun showFirstPageError(message: String) {
        binding.recyclerViewMovies.visibility = View.GONE
        bindingLoading.cardViewLoading.visibility = View.GONE
        bindingError.groupError.visibility = View.VISIBLE
        binding.swipeRefresh.isRefreshing = false
    }

    private fun showNextPageError(message: String) {
        binding.linearLayoutLoadNextPage.visibility = View.GONE
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun onMovieSelected(movie: MoviePageModel) {
        MovieDetailActivity.startActivity(this, movie.id)
    }

    override fun onLoadNextPage() {
        viewModel.loadNextPage()
    }
}