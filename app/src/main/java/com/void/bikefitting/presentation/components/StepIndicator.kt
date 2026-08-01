package com.void.bikefitting.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.void.bikefitting.presentation.theme.OptiBikeColors

/**
 * Step Indicator Component
 * Shows progress through the bike fitting steps
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Composable
fun StepIndicator(
    currentStep: Int,
    totalSteps: Int = 7,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(totalSteps) { step ->
            val isCurrent = step + 1 == currentStep
            val isCompleted = step + 1 < currentStep
            
            StepDot(
                isCurrent = isCurrent,
                isCompleted = isCompleted
            )
            
            if (step < totalSteps - 1) {
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}

@Composable
private fun StepDot(
    isCurrent: Boolean,
    isCompleted: Boolean
) {
    val color = when {
        isCurrent -> OptiBikeColors.PrimaryCyan
        isCompleted -> OptiBikeColors.Success
        else -> OptiBikeColors.DividerColor
    }
    
    Box(
        modifier = Modifier
            .size(12.dp)
            .clip(CircleShape)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        if (isCurrent) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(OptiBikeColors.BackgroundDark)
            )
        }
    }
}

@Composable
fun StepNumberIndicator(
    currentStep: Int,
    totalSteps: Int = 7,
    modifier: Modifier = Modifier
) {
    Text(
        text = "$currentStep/$totalSteps",
        color = OptiBikeColors.PrimaryCyan,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun StepIndicatorPreview() {
    StepIndicator(
        currentStep = 3,
        totalSteps = 7
    )
}

@Preview(showBackground = true)
@Composable
fun StepNumberIndicatorPreview() {
    StepNumberIndicator(
        currentStep = 3,
        totalSteps = 7
    )
}
