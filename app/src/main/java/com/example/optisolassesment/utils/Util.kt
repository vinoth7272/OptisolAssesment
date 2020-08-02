package com.example.optisolassesment.utils

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.optisolassesment.R
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat
import java.util.*
import kotlin.math.abs


fun Context.isNetworkConnected(): Boolean {
    var result = false
    val connectivityManager: ConnectivityManager? =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager?.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            connectivityManager?.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    else -> false
                }

            }
        }
    }
    return result
}

fun View.showShortSnack(message:String){
    Snackbar.make(this,message,Snackbar.LENGTH_SHORT).show()
}


@BindingAdapter(value = ["setText"])
fun TextView.bindText(value: String?) {
    if (value != null && value.isNotEmpty()) {
        this.text = value
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

@BindingAdapter(value = ["setDateText"])
fun TextView.bindDate(value: Long) {
    this.text = convertDatesIntoDays(value)
    this.visibility = View.VISIBLE
}

@BindingAdapter(value = ["setImageUrl"])
fun ImageView.bindImageUrl(imageUrl: String?) {
    if (imageUrl != null && imageUrl.isNotEmpty()) {
        this.visibility = View.VISIBLE
        Glide.with(context)
            .load(imageUrl)
            .placeholder(
                R.drawable.ic_baseline_image_24)
            .into(this)
    } else {
        this.visibility = View.GONE
    }
}

fun convertDatesIntoDays(newsDate: Long): String {
    var different = abs(System.currentTimeMillis() - Date(newsDate*1000).time)

    val secondsInMilli: Long = 1000
    val minutesInMilli = secondsInMilli * 60
    val hoursInMilli = minutesInMilli * 60
    val daysInMilli = hoursInMilli * 24

    val elapsedDays: Long = different / daysInMilli
    different %= daysInMilli
    var convertedString = elapsedDays.convertString("day")
    if (convertedString.checkNull()) return convertedString
    val elapsedHours = different / hoursInMilli
    different %= hoursInMilli
    convertedString = elapsedHours.convertString("hour")
    if (convertedString.checkNull()) return convertedString
    val elapsedMinutes = different / minutesInMilli
    different %= minutesInMilli
    convertedString = elapsedMinutes.convertString("minute")
    if (convertedString.checkNull()) return convertedString
    val elapsedSeconds = different / secondsInMilli
    convertedString = elapsedSeconds.convertString("second")
    if (convertedString.checkNull()) return convertedString
    return newsDate.toString()
}

private fun Long.convertString(appendString: String): String {
    if (this in 1..1) {
        return "$this $appendString ago"
    } else if (this > 1) {
        return "$this ${appendString}s ago"
    }
    return ""
}

fun String.checkNull(): Boolean {
    if (this.isNotEmpty())
        return true
    return false
}

fun distanceBetween(latLng1: LatLng, latLng2: LatLng): String {
    val loc1 = Location(LocationManager.GPS_PROVIDER)
    val loc2 = Location(LocationManager.GPS_PROVIDER)
    loc1.setLatitude(latLng1.latitude)
    loc1.setLongitude(latLng1.longitude)
    loc2.setLatitude(latLng2.latitude)
    loc2.setLongitude(latLng2.longitude)
    return DecimalFormat("##.##").format(loc1.distanceTo(loc2)/1000)
}