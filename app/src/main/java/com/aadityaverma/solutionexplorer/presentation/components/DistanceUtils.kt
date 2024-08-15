package com.aadityaverma.solutionexplorer.presentation.components

import android.location.Location

object DistanceUtils {
    /**
     * Calculate the distance between two geographical points.
     *
     * @param startLatitude Latitude of the starting point.
     * @param startLongitude Longitude of the starting point.
     * @param endLatitude Latitude of the destination point.
     * @param endLongitude Longitude of the destination point.
     * @return Distance in kilometers.
     */
    fun calculateDistance(
        startLatitude: Double,
        startLongitude: Double,
        endLatitude: Double,
        endLongitude: Double
    ): Float {
        val startPoint = Location("locationA").apply {
            latitude = startLatitude
            longitude = startLongitude
        }

        val endPoint = Location("locationB").apply {
            latitude = endLatitude
            longitude = endLongitude
        }

        return startPoint.distanceTo(endPoint) / 1000 // Convert meters to kilometers
    }
}