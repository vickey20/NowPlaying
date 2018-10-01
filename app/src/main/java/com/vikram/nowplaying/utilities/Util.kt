package com.vikram.nowplaying.utilities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.support.v4.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Tasks
import com.vikram.nowplaying.R

private const val SECOND = 1
private const val MINUTE = SECOND * 60
private const val HOUR = MINUTE * 60
private const val DAY = HOUR * 24
private const val MONTH = DAY * 30
private const val YEAR = MONTH * 12

private const val SPLIT_BY = " by "

fun getMinutes(seconds: Long): Long {
    return seconds / MINUTE
}

fun getHours(seconds: Long): Long {
    return seconds / HOUR
}

fun getDays(seconds: Long): Long {
    return seconds / DAY
}

fun getMonths(seconds: Long): Long {
    return seconds / MONTH
}

fun getYears(seconds: Long): Long {
    return seconds / YEAR
}

fun Long.laymanTime(context: Context): String {
    var curr = System.currentTimeMillis()
    var elapsedTime = (curr - this) / 1000
    return when (elapsedTime) {
        in 0..MINUTE -> return "${getString(context, R.string.moments_ago)}"
        in MINUTE..HOUR -> {
            val minutes = getMinutes(elapsedTime)
            return if (minutes > 1) "$minutes ${getString(context, R.string.mins_ago)}" else "$minutes ${getString(context, R.string.min_ago)}"
        }
        in HOUR..DAY -> {
            val hours = getHours(elapsedTime)
            return if (hours > 1) "$hours ${getString(context, R.string.hours_ago)}" else "$hours ${getString(context, R.string.hour_ago)}"
        }
        in DAY..MONTH -> {
            val days = getDays(elapsedTime)
            return if (days > 1) "$days ${getString(context, R.string.days_ago)}" else "$days ${getString(context, R.string.day_ago)}"
        }
        in DAY..YEAR -> {
            val months = getMonths(elapsedTime)
            return if (months > 1) "$months ${getString(context, R.string.months_ago)}" else "$months ${getString(context, R.string.month_ago)}"
        }
        else -> {
            val years = getYears(elapsedTime)
            return if (years > 1) "$years ${getString(context, R.string.years_ago)}" else "$years ${getString(context, R.string.year_ago)}"
        }
    }
}

fun getString(context: Context, resId: Int): String {
    return context.getString(resId)
}

fun getLaymanTime(context: Context, timestamp: Long): String {
    return timestamp.laymanTime(context)
}

fun String.splitIntoTitleAndArtist(): List<String> {
    val lastIndexOfTitle = this.lastIndexOf(SPLIT_BY)
    val title = this.substring(0, lastIndexOfTitle)
    val artist = this.substring(lastIndexOfTitle + SPLIT_BY.length)
    return listOf(title.trim(), artist.trim())
}

private fun isLocationPermissionGranted(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
}

@SuppressLint("MissingPermission")
fun getCurrentLocation(context: Context): Location? {
    if (isLocationPermissionGranted(context)) {
        val locationTask = LocationServices.getFusedLocationProviderClient(context).lastLocation
        return Tasks.await(locationTask)
    }
    return null
}