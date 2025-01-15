package com.example.cvscodechallenge.utils

import android.annotation.SuppressLint
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@SuppressLint("NewApi")
fun String.formatDate(): String {
    return try {
        ZonedDateTime.parse(this)
            .format(DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' HH:mm"))
    } catch (e: DateTimeParseException) {
        "Invalid Date Format"
    }
}