package com.optibike.fitting.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * OptiBike Theme
 * Main theme configuration for the app
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
private val DarkColorScheme = darkColorScheme(
    primary = OptiBikeColors.PrimaryCyan,
    primaryContainer = OptiBikeColors.SurfaceDark,
    onPrimary = OptiBikeColors.BackgroundDark,
    secondary = OptiBikeColors.PrimaryMagenta,
    secondaryContainer = OptiBikeColors.SurfaceDarker,
    onSecondary = OptiBikeColors.BackgroundDark,
    tertiary = OptiBikeColors.NeonGreen,
    background = OptiBikeColors.BackgroundDark,
    onBackground = OptiBikeColors.TextPrimary,
    surface = OptiBikeColors.SurfaceDark,
    onSurface = OptiBikeColors.TextPrimary,
    surfaceVariant = OptiBikeColors.SurfaceDarker,
    onSurfaceVariant = OptiBikeColors.TextSecondary,
    error = OptiBikeColors.Error,
    onError = OptiBikeColors.BackgroundDark,
)

@Composable
fun OptiBikeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> DarkColorScheme // Force dark theme for cyberpunk style
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography.copy(
            displayLarge = MaterialTheme.typography.displayLarge.copy(
                fontWeight = OptiBikeTypography.ExtraBold
            ),
            displayMedium = MaterialTheme.typography.displayMedium.copy(
                fontWeight = OptiBikeTypography.Bold
            ),
            displaySmall = MaterialTheme.typography.displaySmall.copy(
                fontWeight = OptiBikeTypography.Bold
            ),
            headlineLarge = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = OptiBikeTypography.Bold
            ),
            headlineMedium = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = OptiBikeTypography.Medium
            ),
            headlineSmall = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = OptiBikeTypography.Medium
            ),
            titleLarge = MaterialTheme.typography.titleLarge.copy(
                fontWeight = OptiBikeTypography.Bold
            ),
            titleMedium = MaterialTheme.typography.titleMedium.copy(
                fontWeight = OptiBikeTypography.Medium
            ),
            titleSmall = MaterialTheme.typography.titleSmall.copy(
                fontWeight = OptiBikeTypography.Medium
            ),
            bodyLarge = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = OptiBikeTypography.Normal
            ),
            bodyMedium = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = OptiBikeTypography.Normal
            ),
            bodySmall = MaterialTheme.typography.bodySmall.copy(
                fontWeight = OptiBikeTypography.Normal
            ),
            labelLarge = MaterialTheme.typography.labelLarge.copy(
                fontWeight = OptiBikeTypography.Medium
            ),
            labelMedium = MaterialTheme.typography.labelMedium.copy(
                fontWeight = OptiBikeTypography.Medium
            ),
            labelSmall = MaterialTheme.typography.labelSmall.copy(
                fontWeight = OptiBikeTypography.Normal
            )
        ),
        content = content
    )
}
