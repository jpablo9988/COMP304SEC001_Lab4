package com.centennial.juanamorocho_evguenyanstyferov_comp304sec001_lab4.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.centennial.juanamorocho_evguenyanstyferov_comp304sec001_lab4.R
import com.centennial.juanamorocho_evguenyanstyferov_comp304sec001_lab4.viewmodel.MapViewModel
import androidx.compose.foundation.lazy.items


@Composable
fun CategoryScreen(navController: NavHostController, viewModel: MapViewModel) {
    val categoryList = mutableListOf("Education", "Attractions", "Parks", "Nowhere")
    Column(
        modifier = Modifier.padding(16.dp).fillMaxWidth()
    )
    {
        Text(
            "Pick a Category: ", style = typography.titleLarge
        )
        Spacer(Modifier.size(30.dp))
        LazyColumn {
            items(categoryList) { category ->
                CategoryCard(category,
                    onClickAction = { element: String ->
                        navController.navigate("locations/$element")
                    })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MapViewModel, navController: NavHostController) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Lab 4 - Locations in Toronto")
                },
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back_arrow)
                            )
                        }
                    }
                }
            )
        }
    )
    { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Home Screen - Displays the list of products
            composable("home") {
                CategoryScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }
            composable("locations/{category}") {

                    backStackEntry ->
                val category = backStackEntry.arguments?.getString("category")?.toString() ?: ""
                Locations(
                    navController = navController,
                    viewModel = viewModel,
                    category = category
                )
            }
            // Add Product Screen - Allows user to add new products
            composable("map/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
                MapScreen(
                    navController = navController,
                    viewModel = viewModel,
                    id = id
                )
            }
        }
    }
}
@Composable
fun CategoryCard(category : String, onClickAction: (String) -> Unit)
{
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = {onClickAction(category)}
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = category, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.size(10.dp));
                Image( painter = painterResource(id = R.drawable.toronto),
                contentDescription = stringResource(id = R.string.toronto))
            }
        }
    }
}