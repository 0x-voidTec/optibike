package com.void.bikefitting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.void.bikefitting.presentation.navigation.AppNavHost
import com.void.bikefitting.presentation.theme.OptiBikeTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Activity for OptiBike App
 * Entry point for the application UI with Jetpack Compose.
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            OptiBikeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}
