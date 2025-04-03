package com.centennial.juanamorocho_evguenyanstyferov_comp304sec001_lab4.view

import com.centennial.juanamorocho_evguenyanstyferov_comp304sec001_lab4.viewmodel.MapViewModel
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.centennial.juanamorocho_evguenyanstyferov_comp304sec001_lab4.data.TouristLocation
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
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(navController: NavHostController, viewModel: MapViewModel, id: Int) {
    val context = LocalContext.current
    val touristLocations by viewModel.userLocations.collectAsState();
    val touristLocation = touristLocations.firstOrNull { it.id == id }

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
        if (touristLocation != null) {
            position = CameraPosition.fromLatLngZoom(LatLng(touristLocation.lat, touristLocation.long), 12f)
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        properties = properties,
        uiSettings = uiSettings,
        cameraPositionState = cameraPositionState
    ) {
        // Observing user location from ViewModel
        val location = touristLocation?.let { LatLng(it.lat, touristLocation.long) }
        location?.let {
            MarkerComposable(
                state = MarkerState(position = location),
                title = touristLocation.name
            )
            {
                Box(
                    modifier = Modifier
                        .size(100.dp),
                    contentAlignment = Alignment.Center
                )
                {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = null,
                        tint = Color.Blue,
                        modifier = Modifier.matchParentSize()
                    )
                    
                    Text(touristLocation.name, color = Color.White)
                }
            }
            // Move camera to user's location
            LaunchedEffect(location) {
                cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(location, 14f))
            }
        }
    }

}