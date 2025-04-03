package com.centennial.juanamorocho_evguenyanstyferov_comp304sec001_lab4.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.centennial.juanamorocho_evguenyanstyferov_comp304sec001_lab4.data.LocationRepository
import com.centennial.juanamorocho_evguenyanstyferov_comp304sec001_lab4.data.TouristLocation
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MapViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = LocationRepository(application)
    private val _userLocation = MutableStateFlow<LatLng?>(null)
    val userLocation = _userLocation.asStateFlow()
    val userLocations : StateFlow<List<TouristLocation>> = repository.getTouristLocations().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        getUserLocation()
    }

    @SuppressLint("MissingPermission")
    private fun getUserLocation() {
        viewModelScope.launch {
            repository.getUserLocation().collect { location: Location? ->
                location?.let {
                    _userLocation.value = LatLng(it.latitude, it.longitude)
                }
            }
        }
    }
}