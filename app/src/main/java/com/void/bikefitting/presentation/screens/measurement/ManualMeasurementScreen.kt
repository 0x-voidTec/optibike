package com.void.bikefitting.presentation.screens.measurement

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
import com.void.bikefitting.R
import com.void.bikefitting.domain.model.BikeType
import com.void.bikefitting.presentation.navigation.Destinations
import com.void.bikefitting.presentation.theme.OptiBikeColors
import com.void.bikefitting.presentation.theme.OptiBikeTheme
import com.void.bikefitting.presentation.viewmodel.MeasurementViewModel

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
    viewModel: MeasurementViewModel = hiltViewModel()
) {
    val measurementInput by viewModel.measurementInput.collectAsState()
    val validationError by viewModel.validationError.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val saveSuccess by viewModel.saveSuccess.collectAsState()
    
    // Bike type dropdown state
    var bikeTypeExpanded by remember { mutableStateOf(false) }
    
    // Handle successful save
    LaunchedEffect(saveSuccess) {
        if (saveSuccess) {
            navController.navigate(Destinations.RESULTS)
            viewModel.resetForm()
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
            text = stringResource(id = R.string.measurement_manual_title),
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
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Form Fields
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Height
            OutlinedTextField(
                value = measurementInput.height?.toString() ?: "",
                onValueChange = { 
                    viewModel.updateHeight(it.toDoubleOrNull())
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.form_height),
                        color = OptiBikeColors.TextPrimary
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                    focusedBorderColor = OptiBikeColors.PrimaryCyan,
                    unfocusedBorderColor = OptiBikeColors.DividerColor,
                    focusedLabelColor = OptiBikeColors.PrimaryCyan,
                    unfocusedLabelColor = OptiBikeColors.TextSecondary
                )
            )
            
            // Inseam
            OutlinedTextField(
                value = measurementInput.inseam?.toString() ?: "",
                onValueChange = { 
                    viewModel.updateInseam(it.toDoubleOrNull())
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.form_leg_length),
                        color = OptiBikeColors.TextPrimary
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                    focusedBorderColor = OptiBikeColors.PrimaryCyan,
                    unfocusedBorderColor = OptiBikeColors.DividerColor,
                    focusedLabelColor = OptiBikeColors.PrimaryCyan,
                    unfocusedLabelColor = OptiBikeColors.TextSecondary
                )
            )
            
            // Bike Type Dropdown
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
                    label = {
                        Text(
                            text = stringResource(id = R.string.form_bike_type),
                            color = OptiBikeColors.TextPrimary
                        )
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = bikeTypeExpanded
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                        focusedBorderColor = OptiBikeColors.PrimaryCyan,
                        unfocusedBorderColor = OptiBikeColors.DividerColor,
                        focusedLabelColor = OptiBikeColors.PrimaryCyan,
                        unfocusedLabelColor = OptiBikeColors.TextSecondary
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
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(id = R.string.form_bike_type_gravel)) },
                        onClick = {
                            viewModel.updateBikeType(BikeType.GRAVEL)
                            bikeTypeExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
            
            // Optional Fields
            Text(
                text = "Optional Measurements",
                color = OptiBikeColors.TextSecondary,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
            
            // Shoulder Width
            OutlinedTextField(
                value = measurementInput.shoulderWidth?.toString() ?: "",
                onValueChange = { 
                    viewModel.updateShoulderWidth(it.toDoubleOrNull())
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.form_shoulder_width),
                        color = OptiBikeColors.TextPrimary
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                    focusedBorderColor = OptiBikeColors.PrimaryCyan,
                    unfocusedBorderColor = OptiBikeColors.DividerColor,
                    focusedLabelColor = OptiBikeColors.PrimaryCyan,
                    unfocusedLabelColor = OptiBikeColors.TextSecondary
                )
            )
            
            // Arm Length
            OutlinedTextField(
                value = measurementInput.armLength?.toString() ?: "",
                onValueChange = { 
                    viewModel.updateArmLength(it.toDoubleOrNull())
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.form_arm_length),
                        color = OptiBikeColors.TextPrimary
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                    focusedBorderColor = OptiBikeColors.PrimaryCyan,
                    unfocusedBorderColor = OptiBikeColors.DividerColor,
                    focusedLabelColor = OptiBikeColors.PrimaryCyan,
                    unfocusedLabelColor = OptiBikeColors.TextSecondary
                )
            )
            
            // Current Saddle Height
            OutlinedTextField(
                value = measurementInput.currentSaddleHeight?.toString() ?: "",
                onValueChange = { 
                    viewModel.updateCurrentSaddleHeight(it.toDoubleOrNull())
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.form_saddle_height),
                        color = OptiBikeColors.TextPrimary
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                    focusedBorderColor = OptiBikeColors.PrimaryCyan,
                    unfocusedBorderColor = OptiBikeColors.DividerColor,
                    focusedLabelColor = OptiBikeColors.PrimaryCyan,
                    unfocusedLabelColor = OptiBikeColors.TextSecondary
                )
            )
        }
        
        // Validation Error
        if (validationError != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = validationError!!,
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
                contentColor = OptiBikeColors.BackgroundDark,
                disabledContainerColor = OptiBikeColors.DividerColor,
                disabledContentColor = OptiBikeColors.TextDisabled
            ),
            enabled = viewModel.isFormValid() && !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = stringResource(id = R.string.btn_calculate),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Cancel Button
        Button(
            onClick = {
                navController.popBackStack()
            },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = OptiBikeColors.TextSecondary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = stringResource(id = R.string.btn_cancel),
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManualMeasurementScreenPreview() {
    OptiBikeTheme {
        ManualMeasurementScreen(
            navController = rememberNavController()
        )
    }
}
