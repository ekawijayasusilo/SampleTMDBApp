package com.example.sampletmdb.presentation.detail.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.sampletmdb.R
import com.example.sampletmdb.databinding.ActivityMovieDetailBinding
import com.example.sampletmdb.databinding.SublayoutErrorBinding
import com.example.sampletmdb.databinding.SublayoutLoadingBinding
import com.example.sampletmdb.presentation.booking.view.BookMovieWebViewActivity
import com.example.sampletmdb.presentation.detail.model.MovieDetailPageModel
import com.example.sampletmdb.presentation.detail.model.MovieDetailPageState
import com.example.sampletmdb.presentation.detail.viewmodel.MovieDetailViewModel
import com.example.sampletmdb.utils.PageState
import com.example.sampletmdb.utils.convertDpToPx
import com.example.sampletmdb.utils.load
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailActivity : AppCompatActivity() {

    private val viewModel: MovieDetailViewModel by viewModel()
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var bindingError: SublayoutErrorBinding
    private lateinit var bindingLoading: SublayoutLoadingBinding

    companion object {
        const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"

        fun startActivity(activity: Activity, movieId: Int) {
            activity.startActivity(
                Intent(activity, MovieDetailActivity::class.java).apply {
                    putExtra(EXTRA_MOVIE_ID, movieId)
                }
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        bindingError = SublayoutErrorBinding.bind(binding.root)
        bindingLoading = SublayoutLoadingBinding.bind(binding.root)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initUI()
        initObserver()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun initUI() {
        binding.swipeRefresh.setOnRefreshListener { viewModel.reloadMovieDetail() }
    }

    private fun initObserver() {
        viewModel.liveData.observe(::getLifecycle, ::setupView)
        viewModel.initData(intent.getIntExtra(EXTRA_MOVIE_ID, -1))
    }

    private fun setupView(state: PageState<MovieDetailPageState>) {
        when (state) {
            PageState.Loading -> showLoading()
            is PageState.Render -> render(state.renderState)
        }
    }

    private fun showLoading() {
        bindingLoading.cardViewLoading.visibility = View.VISIBLE
        bindingError.groupError.visibility = View.GONE
    }

    private fun render(renderState: MovieDetailPageState) {
        when (renderState) {
            is MovieDetailPageState.Result -> showResult(renderState.movie)
            is MovieDetailPageState.Error -> showError(renderState.message)
        }
    }

    private fun showResult(movie: MovieDetailPageModel) {
        binding.imageViewBackdrop.load(movie.backdropUrl)
        binding.imageViewPoster.load(movie.posterUrl)
        binding.textViewTitle.text = movie.title
        binding.textViewPopularity.text = movie.popularity
        binding.textViewRuntime.text = movie.runtime
        binding.textViewLanguage.text = movie.language
        binding.textViewOverview.text = movie.overview
        binding.linearLayoutGenres.removeAllViews()
        movie.genres.forEach { addGenre(binding.linearLayoutGenres, it) }
        binding.buttonBookMovie.setOnClickListener { BookMovieWebViewActivity.startActivity(this) }

        bindingLoading.cardViewLoading.visibility = View.GONE
        binding.swipeRefresh.isRefreshing = false
    }

    private fun addGenre(parent: ViewGroup, genre: String) {
        parent.addView(
            TextView(this).apply {
                val horizontalPadding = convertDpToPx(this.context, 8)
                val verticalPadding = convertDpToPx(this.context, 4)
                setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding)
                background = getDrawable(R.drawable.shape_rounded_rectangle_primary)
                setTextColor(ContextCompat.getColor(this.context, R.color.colorAccent))
                text = genre
                layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 0, horizontalPadding, 0)
                }
            }
        )
    }

    private fun showError(message: String) {
        binding.swipeRefresh.visibility = View.GONE
        bindingLoading.cardViewLoading.visibility = View.GONE
        bindingError.groupError.visibility = View.VISIBLE
        binding.swipeRefresh.isRefreshing = false
    }
}