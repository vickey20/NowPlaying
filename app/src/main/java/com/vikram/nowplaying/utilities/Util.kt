package com.vikram.nowplaying.utilities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.support.v4.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Tasks

private const val second = 1
private const val minute = second * 60
private const val hour = minute * 60
private const val day = hour * 24
private const val month = day * 30
private const val year = month * 12

fun getMinutes(seconds: Long): Long {
    return seconds / minute
}

fun getHours(seconds: Long): Long {
    return seconds / hour
}

fun getDays(seconds: Long): Long {
    return seconds / day
}

fun getMonths(seconds: Long): Long {
    return seconds / month
}

fun getYears(seconds: Long): Long {
    return seconds / year
}

fun Long.laymanTime(): String {
    var curr = System.currentTimeMillis()
    var elapsedTime = (curr - this) / 1000
    return when (elapsedTime) {
        in 0..minute -> "moments ago"
        in minute..hour -> {
            val minutes = getMinutes(elapsedTime)
            return if (minutes > 1) "$minutes mins ago" else "$minutes min ago"
        }
        in hour..day -> {
            val hours = getHours(elapsedTime)
            return if (hours > 1) "$hours hrs ago" else "$hours hr ago"
        }
        in day..month -> {
            val days = getDays(elapsedTime)
            return if (days > 1) "$days days ago" else "$days day ago"
        }
        in day..year -> {
            val months = getMonths(elapsedTime)
            return if (months > 1) "$months months ago" else "$months month ago"
        }
        else -> {
            val years = getYears(elapsedTime)
            return if (years > 1) "$years years ago" else "$years year ago"
        }
    }
}

fun String.splitIntoTitleAndArtist(): List<String> {
    return this.split("by")
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