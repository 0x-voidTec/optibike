package com.optibike.fitting.presentation.screens.results

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.optibike.fitting.R
import com.optibike.fitting.presentation.navigation.Destinations
import com.optibike.fitting.presentation.theme.OptiBikeColors
import com.optibike.fitting.presentation.theme.OptiBikeTheme
import com.optibike.fitting.presentation.viewmodel.CalculatorViewModel

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
    viewModel: CalculatorViewModel = hiltViewModel(),
    measurementId: Long? = null
) {
    val results by viewModel.results.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    // Trigger calculation for the latest or specific measurement on start
    LaunchedEffect(measurementId) {
        if (measurementId != null) {
            viewModel.loadMeasurementResults(measurementId)
        } else {
            viewModel.calculateLatest()
        }
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
        if (isLoading) {
             Text(
                text = stringResource(id = R.string.loading_latest),
                color = OptiBikeColors.TextSecondary,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        } else {
            results?.let { nonNullResults ->
                SummaryCard(results = nonNullResults, viewModel = viewModel)
                Spacer(modifier = Modifier.height(24.dp))
                
                // Detailed Results
                DetailedResultsCard(results = nonNullResults, viewModel = viewModel)
                Spacer(modifier = Modifier.height(24.dp))
                
                // Recommendations
                if (nonNullResults.recommendations.isNotEmpty()) {
                    RecommendationsCard(recommendations = nonNullResults.recommendations)
                    Spacer(modifier = Modifier.height(24.dp))
                }
            } ?: run {
                Text(
                    text = stringResource(id = R.string.results_no_results),
                    color = OptiBikeColors.TextSecondary,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
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
                text = stringResource(id = R.string.btn_save_to_history),
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
    results: com.optibike.fitting.domain.model.MeasurementResults,
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.results_summary),
                color = OptiBikeColors.PrimaryCyan,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SummaryRow(
                    label = stringResource(id = R.string.results_saddle_height),
                    value = viewModel.getFormattedResult(results.saddleHeight),
                    modifier = Modifier.weight(1f)
                )
                
                SummaryRow(
                    label = stringResource(id = R.string.results_handlebar_height),
                    value = viewModel.getFormattedResult(results.handlebarHeight),
                    modifier = Modifier.weight(1f)
                )
                
                SummaryRow(
                    label = stringResource(id = R.string.results_saddle_tilt),
                    value = viewModel.getFormattedAngle(results.saddleTilt),
                    modifier = Modifier.weight(1f)
                )
            }
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
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            lineHeight = 12.sp
        )
        Text(
            text = value,
            color = OptiBikeColors.PrimaryCyan,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun DetailedResultsCard(
    results: com.optibike.fitting.domain.model.MeasurementResults,
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
                text = stringResource(id = R.string.results_detailed_parameters),
                color = OptiBikeColors.PrimaryCyan,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            ResultItem(
                label = stringResource(id = R.string.results_saddle_fore_aft),
                value = viewModel.getFormattedResult(results.saddleForeAft),
                description = stringResource(id = R.string.results_saddle_fore_aft_desc)
            )
            
            ResultItem(
                label = stringResource(id = R.string.results_saddle_handlebar_distance),
                value = viewModel.getFormattedResult(results.saddleHandlebarDistance),
                description = stringResource(id = R.string.results_saddle_handlebar_distance_desc)
            )
            
            ResultItem(
                label = stringResource(id = R.string.results_handlebar_width),
                value = viewModel.getFormattedResult(results.handlebarWidth),
                description = stringResource(id = R.string.results_handlebar_width_desc)
            )
            
            ResultItem(
                label = stringResource(id = R.string.results_cleat_position),
                value = viewModel.getFormattedResult(results.cleatPosition),
                description = stringResource(id = R.string.results_cleat_position_desc)
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
                text = stringResource(id = R.string.results_recommendations),
                color = OptiBikeColors.NeonGreen,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            recommendations.forEachIndexed { index, recommendationKey ->
                val localizedText = parseRecommendation(recommendationKey)
                Text(
                    text = "${index + 1}. $localizedText",
                    color = OptiBikeColors.TextSecondary,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun parseRecommendation(key: String): String {
    val parts = key.split("|")
    return when (parts[0]) {
        "REC_SADDLE_INCREASE" -> stringResource(id = R.string.rec_saddle_increase, parts[1].toInt())
        "REC_SADDLE_DECREASE" -> stringResource(id = R.string.rec_saddle_decrease, parts[1].toInt())
        "REC_HANDLEBAR_INCREASE" -> stringResource(id = R.string.rec_handlebar_increase, parts[1].toInt())
        "REC_HANDLEBAR_DECREASE" -> stringResource(id = R.string.rec_handlebar_decrease, parts[1].toInt())
        "REC_GRAVEL_GENERAL" -> stringResource(id = R.string.rec_gravel_general)
        else -> key
    }
}

@Preview(showBackground = true)
@Composable
fun ResultsScreenPreview() {
    OptiBikeTheme {
        ResultsScreen(navController = rememberNavController())
    }
}
