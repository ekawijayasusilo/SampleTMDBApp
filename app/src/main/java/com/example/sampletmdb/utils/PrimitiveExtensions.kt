package com.example.sampletmdb.utils

import java.text.SimpleDateFormat
import java.util.*

val locale = Locale("in", "ID")

fun Double.round2Decimal() = String.format("%.2f", this)

fun String?.toFullImageUrl() =
    if (!isNullOrBlank()) "https://image.tmdb.org/t/p/original${this}" else null

fun String.toDate(format: String): Date {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.parse(this)!!
}