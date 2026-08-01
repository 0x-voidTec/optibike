package com.void.bikefitting.presentation.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.void.bikefitting.domain.model.Measurement
import com.void.bikefitting.presentation.navigation.Destinations
import com.void.bikefitting.presentation.theme.OptiBikeColors
import com.void.bikefitting.presentation.theme.OptiBikeTheme
import com.void.bikefitting.presentation.viewmodel.HistoryViewModel
import java.time.format.DateTimeFormatter

/**
 * History Screen
 * Displays list of previous measurements
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Composable
fun HistoryScreen(
    navController: NavController,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val measurements by viewModel.measurements.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(OptiBikeColors.BackgroundDark)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = stringResource(id = R.string.history_title),
            color = OptiBikeColors.TextPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Error message
        error?.let { errorMessage ->
            Text(
                text = errorMessage,
                color = OptiBikeColors.Error,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        // Loading or empty state
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Loading measurements...",
                    color = OptiBikeColors.TextSecondary,
                    fontSize = 16.sp
                )
            }
        } else if (measurements.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.history_empty),
                        color = OptiBikeColors.TextSecondary,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Button(
                        onClick = {
                            navController.navigate(Destinations.MANUAL_MEASUREMENT)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = OptiBikeColors.PrimaryCyan,
                            contentColor = OptiBikeColors.BackgroundDark
                        )
                    ) {
                        Text(
                            text = "Create New Measurement",
                            fontSize = 16.sp
                        )
                    }
                }
            }
        } else {
            // Measurements list
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(measurements) { measurement ->
                    MeasurementCard(
                        measurement = measurement,
                        onClick = {
                            navController.navigate(
                                Destinations.getStepDetailRoute(1) // TODO: Navigate to measurement details
                            )
                        },
                        onDelete = {
                            viewModel.deleteMeasurement(measurement.id)
                        },
                        onExportPdf = {
                            // TODO: Navigate to PDF export with this measurement
                            navController.navigate(Destinations.PDF_EXPORT)
                        }
                    )
                }
            }
            
            // Delete all button
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.deleteAllMeasurements()
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = OptiBikeColors.Error
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.history_delete_all),
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
private fun MeasurementCard(
    measurement: Measurement,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onExportPdf: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Measurement info
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Measurement #${measurement.id}",
                        color = OptiBikeColors.TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    // Date
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                    Text(
                        text = measurement.timestamp.format(formatter),
                        color = OptiBikeColors.TextSecondary,
                        fontSize = 12.sp
                    )
                    
                    // Bike type
                    Text(
                        text = "Bike: ${measurement.bikeType.name}",
                        color = OptiBikeColors.PrimaryCyan,
                        fontSize = 14.sp
                    )
                }
                
                // Menu button
                Box {
                    IconButton(
                        onClick = { showMenu = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More options",
                            tint = OptiBikeColors.TextSecondary
                        )
                    }
                    
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Export PDF") },
                            onClick = {
                                showMenu = false
                                onExportPdf()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Delete", color = OptiBikeColors.Error) },
                            onClick = {
                                showMenu = false
                                showDeleteDialog = true
                            }
                        )
                    }
                }
            }
            
            // Measurement summary
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                measurement.height?.let { height ->
                    InfoChip(label = "Height", value = "${height}cm")
                }
                measurement.inseam?.let { inseam ->
                    InfoChip(label = "Inseam", value = "${inseam}cm")
                }
                measurement.calculatedSaddleHeight?.let { saddleHeight ->
                    InfoChip(label = "Saddle", value = "${saddleHeight.toInt()}mm")
                }
            }
        }
    }
    
    // Delete confirmation dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(
                    text = "Delete Measurement",
                    color = OptiBikeColors.TextPrimary
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to delete this measurement?",
                    color = OptiBikeColors.TextSecondary
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteDialog = false
                        onDelete()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OptiBikeColors.Error,
                        contentColor = OptiBikeColors.BackgroundDark
                    )
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDeleteDialog = false },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = OptiBikeColors.TextSecondary
                    )
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun InfoChip(label: String, value: String) {
    Card(
        modifier = Modifier,
        colors = CardDefaults.cardColors(
            containerColor = OptiBikeColors.BackgroundDarker,
            contentColor = OptiBikeColors.TextPrimary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                color = OptiBikeColors.TextSecondary,
                fontSize = 10.sp
            )
            Text(
                text = value,
                color = OptiBikeColors.PrimaryCyan,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    OptiBikeTheme {
        HistoryScreen(navController = rememberNavController())
    }
}
