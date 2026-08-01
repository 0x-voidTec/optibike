package com.void.bikefitting.presentation.navigation

/**
 * Navigation Destinations
 * Defines all navigation routes for the app
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
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
    
    // Step Detail Arguments
    fun getStepDetailRoute(stepId: Int): String {
        return STEP_DETAIL.replace("{stepId}", stepId.toString())
    }
}
