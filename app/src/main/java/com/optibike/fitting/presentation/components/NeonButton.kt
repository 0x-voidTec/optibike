package com.optibike.fitting.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.optibike.fitting.presentation.theme.OptiBikeColors

/**
 * Neon Button Component
 * Custom button with cyberpunk neon glow effect
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Composable
fun NeonButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    isPrimary: Boolean = true,
    isEnabled: Boolean = true,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    colors: ButtonColors = if (isPrimary) {
        ButtonDefaults.buttonColors(
            containerColor = OptiBikeColors.PrimaryCyan,
            contentColor = OptiBikeColors.BackgroundDark,
            disabledContainerColor = OptiBikeColors.DividerColor,
            disabledContentColor = OptiBikeColors.TextDisabled
        )
    } else {
        ButtonDefaults.outlinedButtonColors(
            contentColor = OptiBikeColors.PrimaryMagenta,
            disabledContentColor = OptiBikeColors.TextDisabled
        )
    }
) {
    Button(
        onClick = onClick,
        modifier = modifier.neonGlow(
            color = if (isPrimary) OptiBikeColors.PrimaryCyan else OptiBikeColors.PrimaryMagenta,
            enabled = isEnabled
        ),
        colors = colors,
        elevation = elevation,
        enabled = isEnabled
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Modifier extension for neon glow effect
 */
private fun Modifier.neonGlow(
    color: Color,
    enabled: Boolean = true
): Modifier = composed {
    if (!enabled) return@composed this
    
    val glowColor = color.copy(alpha = 0.5f)
    val glowOffset = 8.dp
    
    this
        .border(
            width = 1.dp,
            color = color,
            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
        )
        .padding(horizontal = 4.dp, vertical = 2.dp)
}

@Composable
fun NeonOutlinedButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = if (isEnabled) OptiBikeColors.PrimaryCyan else OptiBikeColors.TextDisabled,
            disabledContentColor = OptiBikeColors.TextDisabled
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (isEnabled) OptiBikeColors.PrimaryCyan else OptiBikeColors.DividerColor
        ),
        elevation = null,
        enabled = isEnabled
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NeonButtonPreview() {
    NeonButton(
        onClick = {},
        text = "Primary Button",
        isPrimary = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun NeonOutlinedButtonPreview() {
    NeonOutlinedButton(
        onClick = {},
        text = "Outlined Button",
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    )
}
