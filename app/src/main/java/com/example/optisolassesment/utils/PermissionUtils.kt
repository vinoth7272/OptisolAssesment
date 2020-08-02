package com.example.optisolassesment.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.optisolassesment.R

object PermissionUtils {
    /**
     * Function to request permission from the user
     */
    fun requestAccessFineLocationPermission(activity: Activity?, requestId: Int) {
        activity?.let {
            ActivityCompat.requestPermissions(
                it,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                requestId
            )
        }
    }

    /**
     * Function to check if the location permissions are granted or not
     */
    fun isAccessFineLocationGranted(context: Context?): Boolean {
        return context?.let {
            ContextCompat
                .checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
        } == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Function to check if location of the device is enabled or not
     */
    fun isLocationEnabled(context: Context?): Boolean {
        val locationManager: LocationManager =
            context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    /**
     * Function to show the "enable GPS" Dialog box
     */
    fun showGPSNotEnabledDialog(context: Context?) {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(context.getString(R.string.enable_gps))
                .setMessage(context.getString(R.string.required_for_this_app))
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.enable_now)) { _, _ ->
                    context.startActivity(Intent(ACTION_LOCATION_SOURCE_SETTINGS))
                }
                .show()
        }
    }
}
