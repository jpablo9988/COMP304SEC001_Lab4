package com.centennial.juanamorocho_evguenyanstyferov_comp304sec001_lab4.view

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.centennial.juanamorocho_evguenyanstyferov_comp304sec001_lab4.R
import com.centennial.juanamorocho_evguenyanstyferov_comp304sec001_lab4.data.TouristLocation
import com.centennial.juanamorocho_evguenyanstyferov_comp304sec001_lab4.viewmodel.MapViewModel


@Composable
fun Locations(navController: NavHostController, viewModel: MapViewModel, category:String) {
    val touristLocation by viewModel.userLocations.collectAsState()
    val touristLocationByCategory = touristLocation.filter{ it.category == category }
    //Another Column Lazy List.
    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()) {
        Text(
            "List of Locations: ", style = typography.titleLarge,

        )
        Spacer(Modifier.size(30.dp))
        LazyColumn {
            items(touristLocationByCategory) { location ->
                LocationCard(location,
                    onClickAction = { element: Int ->
                        navController.navigate("map/$element")
                    })
            }
        }
    }
}
@Composable
fun LocationCard(touristLocation: TouristLocation, onClickAction: (Int) -> Unit,
    context: Context  = LocalContext.current)
{
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = {onClickAction(touristLocation.id)}
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = touristLocation.name, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.size(10.dp));
                Image( painter = painterResource(getDrawableID(touristLocation.pictureFileName, context)),
                    contentDescription = stringResource(id = R.string.cntower)
                )
            }
        }
    }
}
fun getDrawableID(name: String?, context: Context): Int {
    val resourceId = context.resources.getIdentifier(
        name,
        "drawable",
        context.packageName
    )
    return resourceId
}