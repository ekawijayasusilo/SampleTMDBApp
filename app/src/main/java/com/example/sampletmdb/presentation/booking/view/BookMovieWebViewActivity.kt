package com.example.sampletmdb.presentation.booking.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sampletmdb.databinding.ActivityBookMovieWebViewBinding
import com.example.sampletmdb.presentation.booking.webview.BookMovieWebViewClient

class BookMovieWebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookMovieWebViewBinding

    companion object {
        private const val EXTRA_URL = "EXTRA_URL"
        private const val BOOK_MOVIE_URL = "https://www.cathaycineplexes.com.sg/"

        fun startActivity(activity: Activity) {
            activity.startActivity(
                Intent(activity, BookMovieWebViewActivity::class.java).apply {
                    putExtra(EXTRA_URL, BOOK_MOVIE_URL)
                }
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookMovieWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = BookMovieWebViewClient()
        binding.webView.loadUrl(intent.getStringExtra(EXTRA_URL))
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}