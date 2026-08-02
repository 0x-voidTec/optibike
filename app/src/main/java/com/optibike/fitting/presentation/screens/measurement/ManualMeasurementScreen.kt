@file:OptIn(ExperimentalMaterial3Api::class)
package com.optibike.fitting.presentation.screens.measurement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    
    // Bike type dropdown state
    var bikeTypeExpanded by remember { mutableStateOf(false) }
    
    // Automatically set bike type from selected bike
    LaunchedEffect(selectedBike) {
        selectedBike?.let {
            viewModel.updateBikeType(it.type)
        }
    }
    
    // Handle successful save
    LaunchedEffect(saveSuccess) {
        if (saveSuccess) {
            navController.navigate(Destinations.RESULTS)
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
        // Title
        Text(
            text = step?.let { stringResource(id = R.string.step_label, it.id) } 
                ?: stringResource(id = R.string.measurement_manual_title),
            color = OptiBikeColors.PrimaryCyan,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))

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
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            textAlign = TextAlign.Center
        )

        selectedBike?.let { bike ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.selected_bike_label, bike.name),
                color = OptiBikeColors.PrimaryCyan,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Form Fields
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Basic fields (always visible)
            
            // Height
            OutlinedTextField(
                value = measurementInput.height?.toString() ?: "",
                onValueChange = { 
                    viewModel.updateHeight(it.toDoubleOrNull())
                },
                label = {
                    Text(text = stringResource(id = R.string.form_height))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                    focusedBorderColor = OptiBikeColors.PrimaryCyan,
                    unfocusedBorderColor = OptiBikeColors.DividerColor
                )
            )
            
            // Inseam
            OutlinedTextField(
                value = measurementInput.inseam?.toString() ?: "",
                onValueChange = { 
                    viewModel.updateInseam(it.toDoubleOrNull())
                },
                label = {
                    Text(text = stringResource(id = R.string.form_leg_length))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                    focusedBorderColor = OptiBikeColors.PrimaryCyan,
                    unfocusedBorderColor = OptiBikeColors.DividerColor
                )
            )
            
            // Bike Type
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
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                        focusedBorderColor = OptiBikeColors.PrimaryCyan,
                        unfocusedBorderColor = OptiBikeColors.DividerColor
                    )
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
            
            // Step-specific fields or All fields if no stepId
            val showAll = stepId == null
            
            // Shoulder Width (Step 6)
            if (showAll || step?.requiredMeasurements?.contains(MeasurementType.SHOULDER_WIDTH) == true) {
                OutlinedTextField(
                    value = measurementInput.shoulderWidth?.toString() ?: "",
                    onValueChange = { viewModel.updateShoulderWidth(it.toDoubleOrNull()) },
                    label = { Text(text = stringResource(id = R.string.form_shoulder_width)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                        focusedBorderColor = OptiBikeColors.PrimaryCyan,
                        unfocusedBorderColor = OptiBikeColors.DividerColor
                    )
                )
            }
            
            // Arm Length (Step 4)
            if (showAll || step?.requiredMeasurements?.contains(MeasurementType.ARM_LENGTH) == true) {
                OutlinedTextField(
                    value = measurementInput.armLength?.toString() ?: "",
                    onValueChange = { viewModel.updateArmLength(it.toDoubleOrNull()) },
                    label = { Text(text = stringResource(id = R.string.form_arm_length)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                        focusedBorderColor = OptiBikeColors.PrimaryCyan,
                        unfocusedBorderColor = OptiBikeColors.DividerColor
                    )
                )
            }
            
            // Current Saddle Height (Recommendations)
            if (showAll || step?.requiredMeasurements?.contains(MeasurementType.SADDLE_HEIGHT) == true) {
                OutlinedTextField(
                    value = measurementInput.currentSaddleHeight?.toString() ?: "",
                    onValueChange = { viewModel.updateCurrentSaddleHeight(it.toDoubleOrNull()) },
                    label = { Text(text = stringResource(id = R.string.form_saddle_height)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                        focusedBorderColor = OptiBikeColors.PrimaryCyan,
                        unfocusedBorderColor = OptiBikeColors.DividerColor
                    )
                )
            }
            
            // Shoe Size (Step 7)
            if (showAll || step?.requiredMeasurements?.contains(MeasurementType.SHOE_SIZE) == true) {
                OutlinedTextField(
                    value = measurementInput.shoeSize?.toString() ?: "",
                    onValueChange = { viewModel.updateShoeSize(it.toIntOrNull()) },
                    label = { Text(text = stringResource(id = R.string.form_shoe_size)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                        focusedBorderColor = OptiBikeColors.PrimaryCyan,
                        unfocusedBorderColor = OptiBikeColors.DividerColor
                    )
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
        
        Spacer(modifier = Modifier.height(24.dp))
        
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
                CircularProgressIndicator(color = OptiBikeColors.BackgroundDark, modifier = Modifier.padding(8.dp))
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
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.outlinedButtonColors(contentColor = OptiBikeColors.TextSecondary),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text(text = stringResource(id = R.string.btn_cancel), fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManualMeasurementScreenPreview() {
    OptiBikeTheme {
        ManualMeasurementScreen(navController = rememberNavController())
    }
}
