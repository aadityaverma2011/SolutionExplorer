package com.aadityaverma.solutionexplorer.domain

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

object LocationManager {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    fun initialize(context: Context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }

    fun getLastLocation(callback: (Location?) -> Unit) {
        if (this::fusedLocationClient.isInitialized) {
            val task: Task<Location?> = fusedLocationClient.lastLocation
            task.addOnSuccessListener { location ->
                callback(location)
            }.addOnFailureListener { exception ->
                exception.printStackTrace()
            }
        } else {
            // Handle case where LocationManager is not initialized
        }
    }

    fun isLocationPermissionGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
}