package com.centennial.juanamorocho_evguenyanstyferov_comp304sec001_lab4.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf

class LocationRepository(context: Context) {
    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private val touristLocations = mutableListOf<TouristLocation>(
        TouristLocation(1,"Centennial College",43.7852, -79.2282, "Education", "centennial"),
        TouristLocation(2,"Seneca",43.7960, -79.3486, "Education", "seneca"),
        TouristLocation(3,"George Brown",43.6758, -79.4107, "Education", "georgebrown"),
        TouristLocation(4,"CN Tower",43.6426, -79.3871, "Attractions", "cntower"),
        TouristLocation(5,"Quttinirpaaq National Park",82.1144, -71.6367, "Parks", "qittnp"),
        TouristLocation(6,"Georgian Bay Islands National Park",44.8743, -79.8700, "Parks", "gpinp"),
        TouristLocation(7,"Who knows?",66.38515, -92.00423, "Nowhere", "nowhere")
    )

    @SuppressLint("MissingPermission")
    fun getUserLocation() = callbackFlow<Location?> {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000).build()
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    trySend(location)
                }
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)

        awaitClose {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }
    fun getTouristLocations() : Flow<List<TouristLocation>>
    {
        return flowOf(touristLocations)
    }

}
