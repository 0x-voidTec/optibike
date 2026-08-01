package com.void.bikefitting.presentation.screens.results

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
 * Results Screen
 * Displays detailed bike fitting results with visualization
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Composable
fun ResultsScreen(
    navController: NavController,
    viewModel: CalculatorViewModel = hiltViewModel()
) {
    val measurement by viewModel.measurement.collectAsState()
    val results by viewModel.results.collectAsState()
    
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
            text = stringResource(id = R.string.results_title),
            color = OptiBikeColors.TextPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        
        Text(
            text = stringResource(id = R.string.results_subtitle),
            color = OptiBikeColors.TextSecondary,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Results Summary
        if (results != null) {
            SummaryCard(results = results!!, viewModel = viewModel)
            Spacer(modifier = Modifier.height(24.dp))
            
            // Detailed Results
            DetailedResultsCard(results = results!!, viewModel = viewModel)
            Spacer(modifier = Modifier.height(24.dp))
            
            // Recommendations
            if (results.recommendations.isNotEmpty()) {
                RecommendationsCard(recommendations = results.recommendations)
                Spacer(modifier = Modifier.height(24.dp))
            }
        } else {
            Text(
                text = "No results available. Please calculate parameters first.",
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
                navController.navigate(Destinations.PDF_EXPORT)
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
                text = stringResource(id = R.string.btn_export_pdf),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = {
                navController.navigate(Destinations.HISTORY)
            },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = OptiBikeColors.TextSecondary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "Save to History",
                fontSize = 14.sp
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = {
                navController.popBackStack()
            },
            colors = ButtonDefaults.textButtonColors(
                contentColor = OptiBikeColors.TextDisabled
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
private fun SummaryCard(
    results: com.void.bikefitting.domain.model.MeasurementResults,
    viewModel: CalculatorViewModel
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = OptiBikeColors.PrimaryCyan.copy(alpha = 0.1f),
            contentColor = OptiBikeColors.TextPrimary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Summary",
                color = OptiBikeColors.PrimaryCyan,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            
            // Key parameters in a row
            SummaryRow(
                label = "Saddle Height",
                value = viewModel.getFormattedResult(results.saddleHeight),
                modifier = Modifier.weight(1f)
            )
            
            SummaryRow(
                label = "Handlebar Height",
                value = viewModel.getFormattedResult(results.handlebarHeight),
                modifier = Modifier.weight(1f)
            )
            
            SummaryRow(
                label = "Saddle Tilt",
                value = viewModel.getFormattedAngle(results.saddleTilt),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            color = OptiBikeColors.TextSecondary,
            fontSize = 12.sp
        )
        Text(
            text = value,
            color = OptiBikeColors.PrimaryCyan,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DetailedResultsCard(
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
                text = "Detailed Parameters",
                color = OptiBikeColors.PrimaryCyan,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            ResultItem(
                label = "Saddle Fore-Aft Position",
                value = viewModel.getFormattedResult(results.saddleForeAft),
                description = "Position relative to bottom bracket"
            )
            
            ResultItem(
                label = "Saddle-Handlebar Distance",
                value = viewModel.getFormattedResult(results.saddleHandlebarDistance),
                description = "Distance between saddle and handlebar"
            )
            
            ResultItem(
                label = "Handlebar Width",
                value = viewModel.getFormattedResult(results.handlebarWidth),
                description = "Recommended handlebar width"
            )
            
            ResultItem(
                label = "Cleat Position",
                value = viewModel.getFormattedResult(results.cleatPosition),
                description = "Position from pedal axis"
            )
        }
    }
}

@Composable
private fun ResultItem(
    label: String,
    value: String,
    description: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            color = OptiBikeColors.TextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        
        Text(
            text = value,
            color = OptiBikeColors.PrimaryCyan,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = description,
            color = OptiBikeColors.TextSecondary,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun RecommendationsCard(recommendations: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = OptiBikeColors.SurfaceDarker,
            contentColor = OptiBikeColors.TextPrimary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Recommendations",
                color = OptiBikeColors.NeonGreen,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            recommendations.forEachIndexed { index, recommendation ->
                Text(
                    text = "${index + 1}. $recommendation",
                    color = OptiBikeColors.TextSecondary,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultsScreenPreview() {
    OptiBikeTheme {
        ResultsScreen(navController = rememberNavController())
    }
}
