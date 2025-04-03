package com.centennial.juanamorocho_evguenyanstyferov_comp304sec001_lab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.centennial.juanamorocho_evguenyanstyferov_comp304sec001_lab4.ui.theme.JuanAmorocho_EvguenyAnstyferov_COMP304SEC001_Lab4Theme
import com.centennial.juanamorocho_evguenyanstyferov_comp304sec001_lab4.view.MainScreen
import com.centennial.juanamorocho_evguenyanstyferov_comp304sec001_lab4.view.MapScreen
import com.centennial.juanamorocho_evguenyanstyferov_comp304sec001_lab4.viewmodel.MapViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController();
            JuanAmorocho_EvguenyAnstyferov_COMP304SEC001_Lab4Theme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainScreen(viewModel, navController)
                }
            }
        }
    }
}

