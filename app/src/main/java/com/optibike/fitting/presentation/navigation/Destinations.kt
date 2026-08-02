package com.optibike.fitting.presentation.navigation

import com.optibike.fitting.R

/**
 * Navigation Destinations
 * Defines all navigation routes for the app
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
data class BottomNavItem(
    val route: String,
    val titleResId: Int,
    val iconResId: Int
)

object Destinations {
    // Splash and Onboarding
    const val SPLASH = "splash"
    const val WELCOME = "welcome"
    const val LANGUAGE_SELECTION = "language_selection"
    
    // Main App Screens
    const val GUIDE = "guide"
    const val STEP_DETAIL = "step_detail/{stepId}"
    const val MANUAL_MEASUREMENT = "manual_measurement"
    const val AR_MEASUREMENT = "ar_measurement"
    const val CALCULATOR = "calculator"
    const val RESULTS = "results"
    const val HISTORY = "history"
    const val SETTINGS = "settings"
    const val PDF_EXPORT = "pdf_export"
    const val BIKE_LIST = "bike_list"
    const val ADD_BIKE = "add_bike"
    
    // Bottom Navigation Items
    val BOTTOM_NAV_ITEMS = listOf(
        BottomNavItem(GUIDE, R.string.nav_guide, R.drawable.ic_guide),
        BottomNavItem(CALCULATOR, R.string.nav_calculator, R.drawable.ic_calculator),
        BottomNavItem(HISTORY, R.string.nav_history, R.drawable.ic_history),
        BottomNavItem(SETTINGS, R.string.nav_settings, R.drawable.ic_settings)
    )
    
    // Step Detail Arguments
    fun getStepDetailRoute(stepId: Int): String {
        return STEP_DETAIL.replace("{stepId}", stepId.toString())
    }
}
