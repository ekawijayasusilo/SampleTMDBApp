package com.example.sampletmdb.presentation.booking.webview

import android.webkit.WebView
import android.webkit.WebViewClient

class BookMovieWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(webView: WebView?, url: String?) = false
}