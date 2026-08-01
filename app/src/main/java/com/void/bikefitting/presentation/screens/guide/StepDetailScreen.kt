package com.void.bikefitting.presentation.screens.guide

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.void.bikefitting.R
import com.void.bikefitting.domain.model.BikeFittingSteps
import com.void.bikefitting.presentation.navigation.Destinations
import com.void.bikefitting.presentation.theme.OptiBikeColors
import com.void.bikefitting.presentation.theme.OptiBikeTheme

/**
 * Step Detail Screen
 * Displays detailed information about a specific bike fitting step
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Composable
fun StepDetailScreen(
    navController: NavController,
    stepId: Int
) {
    val step = BikeFittingSteps.getStepById(stepId)
    
    if (step == null) {
        // Fallback: navigate back to guide
        navController.navigate(Destinations.GUIDE)
        return
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
        // Step Number and Title
        Text(
            text = "Step ${step.id}",
            color = OptiBikeColors.PrimaryCyan,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = stringResource(id = step.titleResId),
            color = OptiBikeColors.TextPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Description Card
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
                    text = stringResource(id = R.string.step_description_label),
                    color = OptiBikeColors.PrimaryCyan,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = stringResource(id = step.descriptionResId),
                    color = OptiBikeColors.TextPrimary,
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Tip Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = OptiBikeColors.SurfaceDarker,
                contentColor = OptiBikeColors.TextSecondary
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.step_tip_label),
                    color = OptiBikeColors.NeonGreen,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = stringResource(id = step.shortDescriptionResId + "_tip"),
                    color = OptiBikeColors.TextSecondary,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Required Measurements
        if (step.requiredMeasurements.isNotEmpty()) {
            Text(
                text = stringResource(id = R.string.step_required_measurements),
                color = OptiBikeColors.TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            step.requiredMeasurements.forEach { measurementType ->
                MeasurementChip(measurementType = measurementType)
                Spacer(modifier = Modifier.height(8.dp))
            }
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
                text = stringResource(id = R.string.btn_manual_measurement),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = {
                navController.navigate(Destinations.AR_MEASUREMENT)
            },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = OptiBikeColors.PrimaryMagenta
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = stringResource(id = R.string.btn_ar_measurement),
                fontSize = 16.sp
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = {
                navController.popBackStack()
            },
            colors = ButtonDefaults.textButtonColors(
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
private fun MeasurementChip(measurementType: com.void.bikefitting.domain.model.MeasurementType) {
    val measurementName = when (measurementType) {
        com.void.bikefitting.domain.model.MeasurementType.HEIGHT -> R.string.form_height
        com.void.bikefitting.domain.model.MeasurementType.INSEAM -> R.string.form_leg_length
        com.void.bikefitting.domain.model.MeasurementType.SHOULDER_WIDTH -> R.string.form_shoulder_width
        com.void.bikefitting.domain.model.MeasurementType.ARM_LENGTH -> R.string.form_arm_length
        com.void.bikefitting.domain.model.MeasurementType.TORSO_LENGTH -> R.string.form_torso_length
        com.void.bikefitting.domain.model.MeasurementType.BIKE_TYPE -> R.string.form_bike_type
        com.void.bikefitting.domain.model.MeasurementType.FRAME_SIZE -> R.string.form_frame_size
        com.void.bikefitting.domain.model.MeasurementType.SADDLE_HEIGHT -> R.string.form_saddle_height
        com.void.bikefitting.domain.model.MeasurementType.HANDLEBAR_HEIGHT -> R.string.form_handlebar_height
        com.void.bikefitting.domain.model.MeasurementType.CRANK_LENGTH -> R.string.form_crank_length
        com.void.bikefitting.domain.model.MeasurementType.SHOE_SIZE -> R.string.form_shoe_size
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = CardDefaults.cardColors(
            containerColor = OptiBikeColors.BackgroundDarker,
            contentColor = OptiBikeColors.TextPrimary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Text(
            text = stringResource(id = measurementName),
            color = OptiBikeColors.TextPrimary,
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}

// Preview with mock step
@Preview(showBackground = true)
@Composable
fun StepDetailScreenPreview() {
    OptiBikeTheme {
        StepDetailScreen(
            navController = rememberNavController(),
            stepId = 1
        )
    }
}
