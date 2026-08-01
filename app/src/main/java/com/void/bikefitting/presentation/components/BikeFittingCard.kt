package com.void.bikefitting.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.void.bikefitting.presentation.theme.OptiBikeColors

/**
 * Bike Fitting Card Component
 * Custom card with cyberpunk styling
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Composable
fun BikeFittingCard(
    modifier: Modifier = Modifier,
    elevation: Dp = 4.dp,
    borderColor: Color = OptiBikeColors.CardStroke,
    borderWidth: Dp = 1.dp,
    backgroundColor: Color = OptiBikeColors.CardBackground,
    contentColor: Color = OptiBikeColors.TextPrimary,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        shape = MaterialTheme.shapes.medium
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = borderWidth,
                    color = borderColor,
                    shape = MaterialTheme.shapes.medium
                )
                .background(backgroundColor)
        ) {
            content()
        }
    }
}

@Composable
fun BikeFittingCardWithHeader(
    title: String,
    modifier: Modifier = Modifier,
    elevation: Dp = 4.dp,
    content: @Composable () -> Unit
) {
    BikeFittingCard(
        modifier = modifier,
        elevation = elevation
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header
            androidx.compose.material3.Text(
                text = title,
                color = OptiBikeColors.PrimaryCyan,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            
            // Content
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BikeFittingCardPreview() {
    BikeFittingCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            androidx.compose.material3.Text(
                text = "Sample Card",
                color = OptiBikeColors.TextPrimary
            )
        }
    }
}
