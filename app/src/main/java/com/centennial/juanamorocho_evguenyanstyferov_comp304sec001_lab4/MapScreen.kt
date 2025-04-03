package com.centennial.juanamorocho_evguenyanstyferov_comp304sec001_lab4

import com.centennial.juanamorocho_evguenyanstyferov_comp304sec001_lab4.viewmodel.MapViewModel
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(viewModel: MapViewModel) {
    val context = LocalContext.current
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
    LaunchedEffect(Unit) {
        if (!locationPermissionGranted.value) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
    val uiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = true, myLocationButtonEnabled= true)) }
    val properties : MapProperties by remember {
        mutableStateOf(MapProperties(isMyLocationEnabled = locationPermissionGranted.value, mapType = MapType.SATELLITE))
    }
    val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(37.7749, -122.4194), 12f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        properties = properties,
        uiSettings = uiSettings,
        cameraPositionState = cameraPositionState
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
                cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(location, 14f))
            }
        }
    }
}