package com.void.bikefitting

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * OptiBike Application Class
 * Main entry point for the application with Hilt dependency injection.
 * 
 * @author Vibe Code (AI Agent)
 * @since 1.0.0
 */
@HiltAndroidApp
class OptiBikeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize any app-wide configurations here
        // Hilt will automatically inject dependencies
    }
}
