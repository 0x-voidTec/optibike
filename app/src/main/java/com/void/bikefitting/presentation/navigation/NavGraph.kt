package com.void.bikefitting.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.void.bikefitting.presentation.screens.ar.ARMeasurementScreen
import com.void.bikefitting.presentation.screens.calculator.CalculatorScreen
import com.void.bikefitting.presentation.screens.guide.GuideScreen
import com.void.bikefitting.presentation.screens.guide.StepDetailScreen
import com.void.bikefitting.presentation.screens.history.HistoryScreen
import com.void.bikefitting.presentation.screens.measurement.ManualMeasurementScreen
import com.void.bikefitting.presentation.screens.results.ResultsScreen
import com.void.bikefitting.presentation.screens.settings.SettingsScreen
import com.void.bikefitting.presentation.screens.splash.SplashScreen
import com.void.bikefitting.presentation.screens.welcome.LanguageSelectionScreen
import com.void.bikefitting.presentation.screens.welcome.WelcomeScreen

/**
 * App Navigation Graph
 * Configures all navigation routes and screens
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.SPLASH
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
        
        // Main App Screens
        composable(Destinations.GUIDE) {
            GuideScreen(navController = navController)
        }
        
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
        
        composable(Destinations.MANUAL_MEASUREMENT) {
            ManualMeasurementScreen(navController = navController)
        }
        
        composable(Destinations.AR_MEASUREMENT) {
            ARMeasurementScreen(navController = navController)
        }
        
        composable(Destinations.CALCULATOR) {
            CalculatorScreen(navController = navController)
        }
        
        composable(Destinations.RESULTS) {
            ResultsScreen(navController = navController)
        }
        
        composable(Destinations.HISTORY) {
            HistoryScreen(navController = navController)
        }
        
        composable(Destinations.SETTINGS) {
            SettingsScreen(navController = navController)
        }
    }
}
