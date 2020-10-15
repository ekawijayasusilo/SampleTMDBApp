package com.example.sampletmdb.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.sampletmdb.R
import kotlin.math.roundToInt

fun ImageView.loadRoundedRectangle(url: String?, roundedValue: Int = 24) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.ic_baseline_image_24)
        .error(R.drawable.ic_baseline_broken_image_24)
        .fallback(R.drawable.ic_baseline_image_24)
        .transform(RoundedCorners(roundedValue))
        .into(this)
}

fun ImageView.load(url: String?) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.ic_baseline_image_24)
        .error(R.drawable.ic_baseline_broken_image_24)
        .fallback(R.drawable.ic_baseline_image_24)
        .into(this)
}

fun getDisplayDensity(context: Context?) = context?.resources?.displayMetrics?.density ?: 0f

fun convertDpToPx(context: Context?, inputDp: Int) =
    (inputDp.toFloat() * getDisplayDensity(context)).roundToInt()