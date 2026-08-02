package com.optibike.fitting

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.optibike.fitting.presentation.components.BottomNavigationBar
import com.optibike.fitting.presentation.navigation.AppNavHost
import com.optibike.fitting.presentation.theme.OptiBikeTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Activity for OptiBike App
 * Entry point for the application UI with Jetpack Compose.
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            OptiBikeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(navController = navController)
                        },
                        modifier = Modifier.fillMaxSize()
                    ) { paddingValues ->
                        AppNavHost(
                            navController = navController,
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                }
            }
        }
    }
}
