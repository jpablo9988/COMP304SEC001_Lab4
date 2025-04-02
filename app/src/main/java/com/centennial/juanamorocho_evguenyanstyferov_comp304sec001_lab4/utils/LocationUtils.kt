package com.centennial.juanamorocho_evguenyanstyferov_comp304sec001_lab4.utils

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import kotlin.math.*

object LocationUtils {

    /**
     * Calculates the distance in meters between two LatLng points using the Haversine formula.
     */
    fun calculateDistance(start: LatLng, end: LatLng): Float {
        val results = FloatArray(1)
        Location.distanceBetween(
            start.latitude, start.longitude,
            end.latitude, end.longitude,
            results
        )
        return results[0] // Distance in meters
    }

    /**
     * Checks if a given location is within a specified radius (in meters) from a center point.
     */
    fun isWithinGeofence(userLocation: LatLng, geofenceCenter: LatLng, radius: Float): Boolean {
        val distance = calculateDistance(userLocation, geofenceCenter)
        return distance <= radius
    }

    /**
     * Converts a LatLng object to a human-readable string.
     */
    fun formatLatLng(location: LatLng): String {
        return "Lat: %.5f, Lng: %.5f".format(location.latitude, location.longitude)
    }
}