package com.optibike.fitting.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.optibike.fitting.presentation.screens.ar.ARMeasurementScreen
import com.optibike.fitting.presentation.screens.calculator.CalculatorScreen
import com.optibike.fitting.presentation.screens.guide.GuideScreen
import com.optibike.fitting.presentation.screens.guide.StepDetailScreen
import com.optibike.fitting.presentation.screens.history.HistoryScreen
import com.optibike.fitting.presentation.screens.measurement.ManualMeasurementScreen
import com.optibike.fitting.presentation.screens.results.PdfExportScreen
import com.optibike.fitting.presentation.screens.results.ResultsScreen
import com.optibike.fitting.presentation.screens.settings.SettingsScreen
import com.optibike.fitting.presentation.screens.splash.SplashScreen
import com.optibike.fitting.presentation.screens.welcome.LanguageSelectionScreen
import com.optibike.fitting.presentation.screens.welcome.WelcomeScreen

/**
 * App Navigation Host
 * Main navigation graph for the application
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.SPLASH,
        modifier = modifier.fillMaxSize()
    ) {
        // Splash and Onboarding
        composable(Destinations.SPLASH) {
            SplashScreen(navController = navController)
        }
        
        composable(Destinations.WELCOME) {
            WelcomeScreen(navController = navController)
        }
        
        composable(Destinations.LANGUAGE_SELECTION) {
            LanguageSelectionScreen(navController = navController)
        }
        
        // Main App Screens (Bottom Navigation)
        composable(Destinations.GUIDE) {
            GuideScreen(navController = navController)
        }
        
        composable(Destinations.CALCULATOR) {
            CalculatorScreen(navController = navController)
        }
        
        composable(Destinations.HISTORY) {
            HistoryScreen(navController = navController)
        }
        
        composable(Destinations.SETTINGS) {
            SettingsScreen(navController = navController)
        }
        
        // Step Details
        composable(
            route = Destinations.STEP_DETAIL,
            arguments = listOf(
                navArgument("stepId") {
                    type = NavType.IntType
                    defaultValue = 1
                }
            )
        ) { backStackEntry ->
            val stepId = backStackEntry.arguments?.getInt("stepId") ?: 1
            StepDetailScreen(
                navController = navController,
                stepId = stepId
            )
        }
        
        // Measurement Screens
        composable(
            route = "${Destinations.MANUAL_MEASUREMENT}?stepId={stepId}",
            arguments = listOf(
                navArgument("stepId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            val stepId = backStackEntry.arguments?.getInt("stepId") ?: 0
            ManualMeasurementScreen(
                navController = navController,
                stepId = if (stepId > 0) stepId else null
            )
        }
        
        composable(Destinations.AR_MEASUREMENT) {
            ARMeasurementScreen(navController = navController)
        }
        
        // Results Screens
        composable(Destinations.RESULTS) {
            ResultsScreen(navController = navController)
        }
        
        composable(Destinations.PDF_EXPORT) {
            PdfExportScreen(navController = navController)
        }
    }
}
