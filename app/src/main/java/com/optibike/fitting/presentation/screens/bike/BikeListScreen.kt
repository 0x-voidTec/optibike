package com.optibike.fitting.presentation.screens.bike

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.optibike.fitting.R
import com.optibike.fitting.domain.model.Bike
import com.optibike.fitting.domain.model.BikeType
import com.optibike.fitting.presentation.components.BikeFittingCard
import com.optibike.fitting.presentation.navigation.Destinations
import com.optibike.fitting.presentation.theme.OptiBikeColors
import com.optibike.fitting.presentation.viewmodel.BikeViewModel

/**
 * Bike List Screen
 * Displays a list of user's bike profiles
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Composable
fun BikeListScreen(
    navController: NavController,
    viewModel: BikeViewModel = hiltViewModel()
) {
    val bikes by viewModel.bikes.collectAsState()
    val selectedBikeId by viewModel.selectedBikeId.collectAsState()

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(OptiBikeColors.BackgroundDark)
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = stringResource(id = R.string.bike_list_title),
                    color = OptiBikeColors.TextPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Destinations.ADD_BIKE) },
                containerColor = OptiBikeColors.PrimaryCyan,
                contentColor = OptiBikeColors.BackgroundDark
            ) {
                Icon(Icons.Default.Add, contentDescription = stringResource(id = R.string.add_bike_button))
            }
        },
        containerColor = OptiBikeColors.BackgroundDark
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
        ) {
            if (bikes.isEmpty()) {
                EmptyBikeList()
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(bikes) { bike ->
                        BikeListItem(
                            bike = bike,
                            isSelected = bike.id == selectedBikeId,
                            onClick = {
                                viewModel.selectBike(bike.id)
                                navController.navigate(Destinations.GUIDE) {
                                    popUpTo(Destinations.BIKE_LIST) {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BikeListItem(
    bike: Bike,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val icon = if (bike.type == BikeType.ROAD) Icons.AutoMirrored.Filled.DirectionsBike else Icons.Default.PedalBike
    
    BikeFittingCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        borderColor = if (isSelected) OptiBikeColors.PrimaryCyan else OptiBikeColors.CardStroke,
        borderWidth = if (isSelected) 2.dp else 1.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = OptiBikeColors.PrimaryCyan,
                modifier = Modifier.size(40.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = bike.name,
                    color = OptiBikeColors.TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (bike.type == BikeType.ROAD) stringResource(id = R.string.form_bike_type_road)
                           else stringResource(id = R.string.form_bike_type_gravel),
                    color = OptiBikeColors.TextSecondary,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun EmptyBikeList() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.DirectionsBike,
            contentDescription = null,
            tint = OptiBikeColors.TextDisabled,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.no_bikes_found),
            color = OptiBikeColors.TextDisabled,
            fontSize = 16.sp
        )
    }
}
