package com.void.bikefitting.presentation.screens.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.void.bikefitting.R
import com.void.bikefitting.presentation.navigation.Destinations
import com.void.bikefitting.presentation.theme.OptiBikeColors
import com.void.bikefitting.presentation.theme.OptiBikeTheme
import com.void.bikefitting.presentation.viewmodel.CalculatorViewModel

/**
 * Calculator Screen
 * Allows users to calculate bike fitting parameters
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Composable
fun CalculatorScreen(
    navController: NavController,
    viewModel: CalculatorViewModel = hiltViewModel()
) {
    val measurement by viewModel.measurement.collectAsState()
    val results by viewModel.results.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    
    // Load latest measurement on start
    LaunchedEffect(Unit) {
        viewModel.calculateLatest()
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(OptiBikeColors.BackgroundDark)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = stringResource(id = R.string.nav_calculator),
            color = OptiBikeColors.TextPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        
        Text(
            text = "Calculate your optimal bike fitting parameters",
            color = OptiBikeColors.TextSecondary,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Error Message
        error?.let { errorMessage ->
            Text(
                text = errorMessage,
                color = OptiBikeColors.Error,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        // Results Card
        if (results != null) {
            ResultsCard(results = results!!, viewModel = viewModel)
            Spacer(modifier = Modifier.height(24.dp))
        } else if (isLoading) {
            Text(
                text = "Loading latest measurement...",
                color = OptiBikeColors.TextSecondary,
                fontSize = 16.sp
            )
        } else {
            Text(
                text = "No measurements found. Please complete a measurement first.",
                color = OptiBikeColors.TextSecondary,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Action Buttons
        Button(
            onClick = {
                navController.navigate(Destinations.MANUAL_MEASUREMENT)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = OptiBikeColors.PrimaryCyan,
                contentColor = OptiBikeColors.BackgroundDark
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "New Measurement",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = {
                navController.navigate(Destinations.GUIDE)
            },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = OptiBikeColors.TextSecondary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = stringResource(id = R.string.btn_back),
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun ResultsCard(
    results: com.void.bikefitting.domain.model.MeasurementResults,
    viewModel: CalculatorViewModel
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = OptiBikeColors.CardBackground,
            contentColor = OptiBikeColors.TextPrimary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Your Bike Fitting Results",
                color = OptiBikeColors.PrimaryCyan,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            // Saddle Height
            ResultRow(
                label = "Saddle Height",
                value = viewModel.getFormattedResult(results.saddleHeight),
                unit = "mm"
            )
            
            // Saddle Tilt
            ResultRow(
                label = "Saddle Tilt",
                value = viewModel.getFormattedAngle(results.saddleTilt),
                unit = ""
            )
            
            // Saddle Fore-Aft
            ResultRow(
                label = "Saddle Fore-Aft",
                value = viewModel.getFormattedResult(results.saddleForeAft),
                unit = "mm"
            )
            
            // Handlebar Height
            ResultRow(
                label = "Handlebar Height",
                value = viewModel.getFormattedResult(results.handlebarHeight),
                unit = "mm"
            )
            
            // Saddle-Handlebar Distance
            ResultRow(
                label = "Saddle-Handlebar Distance",
                value = viewModel.getFormattedResult(results.saddleHandlebarDistance),
                unit = "mm"
            )
            
            // Handlebar Width
            ResultRow(
                label = "Handlebar Width",
                value = viewModel.getFormattedResult(results.handlebarWidth),
                unit = "mm"
            )
            
            // Cleat Position
            ResultRow(
                label = "Cleat Position",
                value = viewModel.getFormattedResult(results.cleatPosition),
                unit = "mm"
            )
            
            // Recommendations
            if (results.recommendations.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Recommendations:",
                    color = OptiBikeColors.NeonGreen,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                
                results.recommendations.forEachIndexed { index, recommendation ->
                    Text(
                        text = "• $recommendation",
                        color = OptiBikeColors.TextSecondary,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ResultRow(
    label: String,
    value: String,
    unit: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = OptiBikeColors.TextPrimary,
            fontSize = 14.sp
        )
        
        Text(
            text = "$value $unit",
            color = OptiBikeColors.PrimaryCyan,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun Row(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    content: @Composable () -> Unit
) {
    androidx.compose.foundation.layout.Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    OptiBikeTheme {
        CalculatorScreen(navController = rememberNavController())
    }
}
