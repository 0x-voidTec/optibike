@file:OptIn(ExperimentalMaterial3Api::class)
package com.optibike.fitting.presentation.screens.measurement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.optibike.fitting.R
import com.optibike.fitting.domain.model.BikeFittingSteps
import com.optibike.fitting.domain.model.BikeType
import com.optibike.fitting.domain.model.MeasurementType
import com.optibike.fitting.presentation.navigation.Destinations
import com.optibike.fitting.presentation.theme.OptiBikeColors
import com.optibike.fitting.presentation.theme.OptiBikeTheme
import com.optibike.fitting.presentation.viewmodel.MeasurementViewModel

/**
 * Manual Measurement Screen
 * Form for entering bike fitting measurements manually
 * Optimized to show only relevant fields for a specific step
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Composable
fun ManualMeasurementScreen(
    navController: NavController,
    viewModel: MeasurementViewModel = hiltViewModel(),
    stepId: Int? = null
) {
    val measurementInput by viewModel.measurementInput.collectAsState()
    val selectedBike by viewModel.selectedBike.collectAsState()
    val validationError by viewModel.validationError.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val saveSuccess by viewModel.saveSuccess.collectAsState()
    
    var bikeTypeExpanded by remember { mutableStateOf(false) }
    
    LaunchedEffect(selectedBike) {
        selectedBike?.let {
            viewModel.updateBikeType(it.type)
        }
    }
    
    LaunchedEffect(saveSuccess) {
        if (saveSuccess) {
            navController.navigate(Destinations.RESULTS)
            viewModel.consumeSaveSuccess()
            viewModel.resetForm()
        }
    }
    
    val scrollState = rememberScrollState()
    val step = stepId?.let { BikeFittingSteps.getStepById(it) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(OptiBikeColors.BackgroundDark)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Step Badge
        step?.let {
            Surface(
                color = OptiBikeColors.PrimaryCyan.copy(alpha = 0.2f),
                contentColor = OptiBikeColors.PrimaryCyan,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.step_label, it.id),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Title
        Text(
            text = step?.let { stringResource(id = it.titleResId) }
                ?: stringResource(id = R.string.measurement_manual_title),
            color = OptiBikeColors.TextPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        
        Text(
            text = stringResource(id = R.string.measurement_manual_description),
            color = OptiBikeColors.TextSecondary,
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            textAlign = TextAlign.Center
        )

        selectedBike?.let { bike ->
            Card(
                modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = OptiBikeColors.SurfaceDark),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.selected_bike_label, bike.name),
                    color = OptiBikeColors.PrimaryCyan,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(12.dp).fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Form Fields - Dynamic based on step
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Helper to check if field should be shown
            fun shouldShow(type: MeasurementType) = stepId == null || step?.requiredMeasurements?.contains(type) == true

            // Height (always show as base)
            if (shouldShow(MeasurementType.HEIGHT)) {
                FormField(
                    value = measurementInput.height?.toString() ?: "",
                    label = stringResource(id = R.string.form_height),
                    onValueChange = { viewModel.updateHeight(it.toDoubleOrNull()) }
                )
            }
            
            // Inseam (always show as base)
            if (shouldShow(MeasurementType.INSEAM)) {
                FormField(
                    value = measurementInput.inseam?.toString() ?: "",
                    label = stringResource(id = R.string.form_leg_length),
                    onValueChange = { viewModel.updateInseam(it.toDoubleOrNull()) }
                )
            }
            
            // Bike Type (only if no bike selected or manual mode)
            if (selectedBike == null && shouldShow(MeasurementType.BIKE_TYPE)) {
                ExposedDropdownMenuBox(
                    expanded = bikeTypeExpanded,
                    onExpandedChange = { bikeTypeExpanded = !bikeTypeExpanded }
                ) {
                    OutlinedTextField(
                        value = when (measurementInput.bikeType) {
                            BikeType.ROAD -> stringResource(id = R.string.form_bike_type_road)
                            BikeType.GRAVEL -> stringResource(id = R.string.form_bike_type_gravel)
                        },
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(text = stringResource(id = R.string.form_bike_type)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = bikeTypeExpanded) },
                        modifier = Modifier.fillMaxWidth().menuAnchor(),
                        colors = textFieldColors()
                    )
                    
                    ExposedDropdownMenu(
                        expanded = bikeTypeExpanded,
                        onDismissRequest = { bikeTypeExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.form_bike_type_road)) },
                            onClick = {
                                viewModel.updateBikeType(BikeType.ROAD)
                                bikeTypeExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.form_bike_type_gravel)) },
                            onClick = {
                                viewModel.updateBikeType(BikeType.GRAVEL)
                                bikeTypeExpanded = false
                            }
                        )
                    }
                }
            }
            
            // Optional/Step-specific fields
            if (shouldShow(MeasurementType.SHOULDER_WIDTH)) {
                FormField(
                    value = measurementInput.shoulderWidth?.toString() ?: "",
                    label = stringResource(id = R.string.form_shoulder_width),
                    onValueChange = { viewModel.updateShoulderWidth(it.toDoubleOrNull()) }
                )
            }
            
            if (shouldShow(MeasurementType.ARM_LENGTH)) {
                FormField(
                    value = measurementInput.armLength?.toString() ?: "",
                    label = stringResource(id = R.string.form_arm_length),
                    onValueChange = { viewModel.updateArmLength(it.toDoubleOrNull()) }
                )
            }
            
            if (shouldShow(MeasurementType.SADDLE_HEIGHT)) {
                FormField(
                    value = measurementInput.currentSaddleHeight?.toString() ?: "",
                    label = stringResource(id = R.string.form_saddle_height),
                    onValueChange = { viewModel.updateCurrentSaddleHeight(it.toDoubleOrNull()) }
                )
            }
            
            if (shouldShow(MeasurementType.SHOE_SIZE)) {
                FormField(
                    value = measurementInput.shoeSize?.toString() ?: "",
                    label = stringResource(id = R.string.form_shoe_size),
                    onValueChange = { viewModel.updateShoeSize(it.toIntOrNull()) }
                )
            }
        }
        
        // Validation Error
        if (validationError != null) {
            Spacer(modifier = Modifier.height(16.dp))
            val errorMessage = when (validationError) {
                "ERR_HEIGHT_RANGE" -> stringResource(id = R.string.error_height_range)
                "ERR_INSEAM_RANGE" -> stringResource(id = R.string.error_leg_length_range)
                "ERR_BIKE_TYPE_REQUIRED" -> stringResource(id = R.string.error_required_field)
                else -> validationError!!
            }
            Text(
                text = errorMessage,
                color = OptiBikeColors.Error,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Calculate Button
        Button(
            onClick = {
                viewModel.saveMeasurement()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = OptiBikeColors.PrimaryCyan,
                contentColor = OptiBikeColors.BackgroundDark
            ),
            enabled = viewModel.isFormValid() && !isLoading,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = OptiBikeColors.BackgroundDark, modifier = Modifier.size(24.dp))
            } else {
                Text(
                    text = stringResource(id = R.string.btn_calculate),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Cancel Button
        TextButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text(
                text = stringResource(id = R.string.btn_cancel),
                color = OptiBikeColors.TextSecondary,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun FormField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        colors = textFieldColors()
    )
}

@Composable
fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = OptiBikeColors.PrimaryCyan,
    unfocusedBorderColor = OptiBikeColors.CardStroke,
    focusedLabelColor = OptiBikeColors.PrimaryCyan,
    unfocusedLabelColor = OptiBikeColors.TextSecondary,
    cursorColor = OptiBikeColors.PrimaryCyan,
    focusedTextColor = OptiBikeColors.TextPrimary,
    unfocusedTextColor = OptiBikeColors.TextPrimary
)

@Preview(showBackground = true)
@Composable
fun ManualMeasurementScreenPreview() {
    OptiBikeTheme {
        ManualMeasurementScreen(navController = rememberNavController())
    }
}
