package com.centennial.juanamorocho_evguenyanstyferov_comp304sec001_lab4

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.centennial.juanamorocho_evguenyanstyferov_comp304sec001_lab4.viewmodel.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*

@Composable
fun MapScreen(viewModel: MapViewModel) {
    val context = LocalContext.current

    // Permission state for location
    val locationPermissionGranted = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            locationPermissionGranted.value = isGranted
        }

    // Request permission on launch
    LaunchedEffect(Unit) {
        if (!locationPermissionGranted.value) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    // Use remember to store properties
    val uiSettings by remember { mutableStateOf(MapUiSettings()) }
    val properties by remember { mutableStateOf(MapProperties(isMyLocationEnabled = locationPermissionGranted.value)) }
    val cameraPositionState by remember { mutableStateOf(CameraPositionsState()) }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        properties = properties,
        uiSettings = uiSettings,
        cameraPositionState = cameraPositionState.state
    ) {
        // Observing user location from ViewModel
        val userLocation by viewModel.userLocation.collectAsState()

        userLocation?.let { location ->
            Marker(
                state = MarkerState(position = location),
                title = "You are here",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
            )

            // Move camera to user's location
            LaunchedEffect(location) {
                cameraPositionState.state.animate(CameraUpdateFactory.newLatLngZoom(location, 14f))
            }
        }
    }
}

fun Marker(state: MarkerState, title: String, icon: BitmapDescriptor) {
    TODO("Not yet implemented")
}

class MarkerState(position: LatLng) {

}

fun GoogleMap(
    modifier: Modifier,
    properties: MapProperties,
    uiSettings: MapUiSettings,
    cameraPositionState: Any,
    function: @Composable () -> Unit?,
) {
    TODO("Not yet implemented")
}

/**
 * Data class to manage UI settings for Google Maps.
 */
data class MapUiSettings(
    val zoomControlsEnabled: Boolean = true,
    val myLocationButtonEnabled: Boolean = true
)

/**
 * Data class to manage Google Map properties.
 */
data class MapProperties(
    val isMyLocationEnabled: Boolean
)

/**
 * Class to manage Camera Position State.
 */
class CameraPositionsState {
    val state: CameraPositionState<Any?> = CameraPositionState(
        position = CameraPosition.fromLatLngZoom(LatLng(37.7749, -122.4194), 12f) // Default to San Francisco
    )

    class CameraPositionState<CameraUpdate>(position: CameraPosition) {
        fun animate(newLatLngZoom: CameraUpdate) {

        }

    }
}